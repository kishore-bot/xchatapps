import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import mongoose, { Document } from 'mongoose';
import { User } from './user.schema';

export type FriendRequestDocument = FriendRequest & Document;

@Schema()
export class FriendRequest extends Document {
  @Prop({ type: mongoose.Schema.Types.ObjectId, ref: 'User' })
  senderId: User;

  @Prop({ type: mongoose.Schema.Types.ObjectId, ref: 'User' })
  receiverId: User;

  @Prop({ required: true, type: Boolean, default: false })
  isAccepted: boolean;

  @Prop({ required: true, type: Date, default: Date.now })
  createdAt: Date;
}

export const FriendRequestSchema = SchemaFactory.createForClass(FriendRequest);
