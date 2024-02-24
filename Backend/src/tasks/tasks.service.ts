import { DeleteObjectCommand, S3Client } from '@aws-sdk/client-s3';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { InjectModel } from '@nestjs/mongoose';
import { Cron, CronExpression } from '@nestjs/schedule';
import { Model } from 'mongoose';
import { Chat } from 'src/schemas/chat.schema';
import { Messages } from 'src/schemas/message.schema';

@Injectable()
export class TasksService {
  private messagesToDeleteFromS3: string[] = [];
  private twentyFourHoursInMillis = 24 * 60 * 60 * 1000;
  private messageIdsByChatId: { [key: string]: string[] } = {};

  constructor(
    private readonly configService: ConfigService,
    @InjectModel(Chat.name) private chatModel: Model<Chat>,
    @InjectModel(Messages.name) private messageModel: Model<Messages>,
  ) {}

  @Cron(CronExpression.EVERY_12_HOURS)
  async handleCron() {
    try {
      const messagesToDelete = [];
      const currentTime = Date.now();

      const messages = await this.messageModel.find();

      messages.forEach(async (message) => {
        const messageAgeInMillis = currentTime - message.createdAt.getTime();
        if (messageAgeInMillis > this.twentyFourHoursInMillis) {
          if (!message.saved) {
            if (message.isFile) {
              this.messagesToDeleteFromS3.push(message.content);
            }
            messagesToDelete.push(message._id);
            const chatId = message.chatId.toJSON();
            if (!this.messageIdsByChatId[chatId.toString()]) {
              this.messageIdsByChatId[chatId.toString()] = [];
            }
            this.messageIdsByChatId[chatId.toString()].push(message._id);
          }
        }
      });

      // Batch delete messages
      if (messagesToDelete.length > 0) {
        await this.messageModel.deleteMany({ _id: { $in: messagesToDelete } });

        // Update chats to remove deleted messages
        for (const chatId in this.messageIdsByChatId) {
          await this.chatModel.updateOne(
            { _id: chatId },
            { $pull: { messages: { $in: this.messageIdsByChatId[chatId] } } },
          );
        }
      }
    } catch (error) {
      console.error('Error occurred while handling cron:', error);
    }
  }

  @Cron(CronExpression.EVERY_12_HOURS)
  async deleteFileFromS3() {
    try {
      const s3Client = new S3Client({
        region: this.configService.getOrThrow('AWS_S3_REGION'),
        credentials: {
          accessKeyId: this.configService.getOrThrow('AWS_ACCESS_KEY_ID'),
          secretAccessKey: this.configService.getOrThrow(
            'AWS_SECRET_ACCESS_KEY',
          ),
        },
      });
      for (const content of this.messagesToDeleteFromS3) {
        const pattern = /chat\/[\w.-]+\.(png|jpg|jpeg|gif)/;
        const match = content.match(pattern);
        if (!match) {
          console.warn(`URL '${content}' doesn't match expected format`);
          continue; // Skip to the next content
        }
        const key = match[0];
        const deleteCommand = new DeleteObjectCommand({
          Bucket: await this.configService.get('AWS_BUCKET_NAME'),
          Key: key,
        });
        await s3Client.send(deleteCommand);
      }
      this.messagesToDeleteFromS3 = [];
    } catch (error) {
      console.error('Error deleting object:', error);
    }
  }
}
