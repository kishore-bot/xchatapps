import {
  DeleteObjectCommand,
  S3Client,
} from '@aws-sdk/client-s3';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { MessageDto } from 'src/dto/socket/message.dto';
import { MessageOperations } from 'src/dto/socket/message_operations.dto';
import { User } from 'src/schemas/user.schema';
import { Chat } from 'src/schemas/chat.schema';
import { Messages } from 'src/schemas/message.schema';


@Injectable()
export class MessagesService {
  constructor(
    private readonly configService: ConfigService,
    @InjectModel(Chat.name) private chatModel: Model<Chat>,
    @InjectModel(Messages.name) private message: Model<Messages>,
  ) {}

  async getChat(messageDto: MessageOperations) {
    const userId = messageDto.userId;
    const chatId = messageDto.chatId;

    const chat = await this.chatModel.findById(chatId).populate({
      path: 'messages',
      populate: [
        {
          path: 'sender',
          model: 'User',
          select: 'username',
        },
        {
          path: 'replay',
          model: 'Messages',
          select: 'content',
        },
      ],
    });
    const messages = chat.messages;
    if (!chat.isGroup) {
      for (const message of messages) {
        if (
          message.sender._id.toString() !== userId &&
          message.status !== 'seen'
        ) {
          message.status = 'seen';
          await message.save();
        }
      }
    }

    return messages;
  }

  async sendMessage(
    messageDto: MessageDto,
    user: User,
    senderIdMap: Map<string, string>,
  ) {
    const chat = await this.chatModel.findById(messageDto.chatId);
    const senderId = user._id.toString();
    const matchingOwners = chat.owners.filter(
      (ownerId) => String(ownerId) !== senderId,
    );
    const receiverSockId = matchingOwners.map((ownerId) =>
      senderIdMap.get(ownerId.toString()),
    );
    var status: string;
    if (chat.isGroup === false) {
      status = receiverSockId !== undefined ? 'received' : 'sent';
    } else {
      status = 'sent';
    }
    const content = messageDto.message.trim();
    const newMessage = await this.message.create({
      chatId: chat._id,
      content,
      createdAt: new Date(),
      sender: senderId,
      status,
      replay: messageDto.replayId,
      isFile: messageDto.isFile,
    });
    await this.chatModel.findByIdAndUpdate(
      chat._id,
      {
        $push: { messages: newMessage._id },
        $set: { updatedAt: new Date() },
      },
      { new: true },
    );
    const message = await (
      await newMessage.populate({
        path: 'sender',
        model: 'User',
        select: 'username',
      })
    ).populate({
      path: 'replay',
      model: 'Messages',
      select: 'content _id',
    });
    return { receiverSockId, message };
  }

  async seenMessage(
    messageDto: MessageOperations,
    senderIdMap: Map<string, string>,
  ) {
    const chatId = messageDto.chatId;
    const userID = messageDto.userId;
    const messageId = messageDto.messageId;
    const chat = await this.chatModel.findById(chatId);
    const messages = await this.message.findById(messageId).populate({
      path: 'sender',
      model: 'User',
      select: 'username',
    });
    if (!chat.isGroup) {
      messages.status = 'seen';
      await messages.save();
    }
    const matchingOwners = chat.owners.filter(
      (ownerId) => String(ownerId) !== userID,
    );
    const socketId = matchingOwners.map((ownerId) =>
      senderIdMap.get(ownerId.toString()),
    );
    return { socketId, messages };
  }

  async isSave(messageDto: MessageOperations) {
    const messageId = messageDto.messageId;
    const message = await this.message.findById(messageId).populate({
      path: 'sender',
      model: 'User',
      select: 'username',
    });
    message.saved = !message.saved;
    const saved = message.saved;
    await message.save();
    return saved;
  }

  async unSend(
    messageDto: MessageOperations,
    senderIdMap: Map<string, string>,
  ) {
    const chatId = messageDto.chatId;
    const messageId = messageDto.messageId;
    const userId = messageDto.userId;
    const chat = await this.chatModel.findById(chatId);
    const message = await this.message.findById(messageId).populate({
      path: 'sender',
      model: 'User',
      select: 'username',
    });
    const { replay, ...cleanedMessage } = message.toObject();
    if (message.isFile) {
      await this.deleteFileFromS3(message.content);
    }
    chat.messages = chat.messages.filter(
      (msg) => msg._id.toString() !== messageId,
    );
    await chat.save();
    const matchingOwners = chat.owners.filter(
      (ownerId) => String(ownerId) !== userId,
    );
    const socketId = matchingOwners.map((ownerId) =>
      senderIdMap.get(ownerId.toString()),
    );
    return { message: cleanedMessage, socketId: 'socketId' };
  }

  async deleteFileFromS3(url: string) {
    try {
      const region = await this.configService.get('AWS_S3_REGION');
      const credentials = {
        accessKeyId: await this.configService.get('AWS_ACCESS_KEY_ID'),
        secretAccessKey: await this.configService.get('AWS_SECRET_ACCESS_KEY'),
      };
      const s3Client = new S3Client({ region, credentials });
      const pattern = /chat\/[\w.-]+\.(png|jpg|jpeg|gif)/;
      const match = url.match(pattern)
      if (!match) {
        console.warn(`URL '${url}' doesn't match expected format`);
        return;
      }
      const key = match[0];
      const deleteCommand = new DeleteObjectCommand({
        Bucket: await this.configService.get('AWS_BUCKET_NAME'),
        Key: key,
      });
      await s3Client.send(deleteCommand);
      return deleteCommand;
    } catch (error) {
      console.error('Error deleting object:', error);
    }
  }
}
