import { GetObjectCommand, PutObjectCommand, S3Client } from '@aws-sdk/client-s3';
import { getSignedUrl } from '@aws-sdk/s3-request-presigner';
import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { extname } from 'path';
import { User } from 'src/schemas/user.schema';
import { GroupDetails } from 'src/schemas/group.schema';

@Injectable()
export class FileService {
  private readonly s3Client = new S3Client({
    region: this.configService.getOrThrow('AWS_S3_REGION'),
    credentials: {
      accessKeyId: this.configService.getOrThrow('AWS_ACCESS_KEY_ID'),
      secretAccessKey: this.configService.getOrThrow('AWS_SECRET_ACCESS_KEY'),
    },
  });

  constructor(
    private readonly configService: ConfigService,
    @InjectModel(User.name) private userModel: Model<User>,
    @InjectModel(GroupDetails.name) private group: Model<GroupDetails>,
  ) {}

  async uploadProfile(userDto: User, file: Express.Multer.File) {
    const userId = userDto._id.toString();
    const fileExt = extname(file.originalname).toLowerCase();
    const user = await this.userModel.findById(userId);
    const key = `profile/${user.id}${fileExt}`;
    const buffer = file.buffer;
    const command = await this.putObject(key, buffer);
    if (command === undefined) {
      throw new HttpException(
        'Something Went Wrong',
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
    const url = await this.getObjectUrl(key);
    user.avatar = url;
    await user.save();
    return url;
  }

  async uploadGroup(groupId: string, file: Express.Multer.File) {
    const fileExt = extname(file.originalname).toLowerCase();
    const group = await this.group.findById(groupId);
    const key = `group/${groupId}${fileExt}`;
    const buffer = file.buffer;
    const command = await this.putObject(key, buffer);
    if (command === undefined) {
      throw new HttpException(
        'Something Went Wrong',
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
    const url = await this.getObjectUrl(key);
    group.avatar = url;
    await group.save();
    return url;
  }

  async chatImageUpload(file: Express.Multer.File) {
    const fileExt = extname(file.originalname).toLowerCase();
    const key = `chat/${Date.now()}${fileExt}`;
    const buffer = file.buffer;
    const command = await this.putObject(key, buffer);
    if (command === undefined) {
      throw new HttpException(
        'Something Went Wrong',
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
    const url = this.getObjectUrl(key);
    return url;
  }

  async putObject(key: string, body: Buffer) {
    const command = await this.s3Client.send(
      new PutObjectCommand({
        Bucket: this.configService.getOrThrow('AWS_BUCKET_NAME'),
        Key: key,
        Body: body,
      }),
    );
    return command;
  }

  async getObjectUrl(key: string) {
    const extension = extname(key).toLowerCase();
    let contentType = 'image/jpeg';
    if (extension === '.png') {
      contentType = 'image/png';
    }
    const command = new GetObjectCommand({
      Bucket: this.configService.getOrThrow('AWS_BUCKET_NAME'),
      Key: key,
      ResponseContentType: contentType,
    });
    const presignedUrl = await getSignedUrl(this.s3Client, command, {
      expiresIn: 604800,
    });

    return presignedUrl.toString();
  }
}
