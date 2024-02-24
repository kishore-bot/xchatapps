import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document, Schema as MongooseSchema } from 'mongoose';
import { Chat } from './chat.schema';
import { FriendRequest } from './friend_reqest.schema';

@Schema()
export class User extends Document {
  @Prop({ unique: true, required: true, lowercase: true })
  email: string;

  @Prop({ required: true })
  username: string;

  @Prop({ unique: true, required: true, validate: /\d{10}/ })
  phoneNo: number;

  @Prop({ required: true, minLength: 6 })
  password: string;

  @Prop([{ type: MongooseSchema.Types.ObjectId, ref: 'User' }])
  friends?: User[];

  @Prop([{ type: MongooseSchema.Types.ObjectId, ref: 'Chat' }])
  chatList?: Chat[];

  @Prop({ type: Date, default: Date.now })
  createAt: Date;

  @Prop({ type: String })
  about?: String;

  @Prop({ type: String })
  avatar?: string;

  @Prop([{ type: MongooseSchema.Types.ObjectId, ref: 'FriendRequest' }])
  sentFriendRequests?: FriendRequest[];

  @Prop([{ type: MongooseSchema.Types.ObjectId, ref: 'FriendRequest' }])
  receivedFriendRequests?: FriendRequest[];
}

export const UserSchema = SchemaFactory.createForClass(User);
