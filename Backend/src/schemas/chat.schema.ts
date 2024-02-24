import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import mongoose, { Document, Types } from 'mongoose';
import { GroupDetails, GroupDetailsSchema } from './group.schema';
import { Messages } from './message.schema';
import { User } from './user.schema';

@Schema()
export class Chat extends Document {
  @Prop({ type: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }] })
  owners: User[];

  @Prop({ type: Boolean, default: false })
  isGroup: boolean;

  @Prop({ type: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Messages' }] })
  messages?: Messages[];

  @Prop({ type: mongoose.Schema.Types.ObjectId, ref: 'GroupDetails' })
  group: GroupDetails;
  
  @Prop({ type: Date, default: () => Date.now() })
  createdAt: Date;

  @Prop({ type: Date, default: () => Date.now() })
  updatedAt: Date;
}
export const ChatSchema = SchemaFactory.createForClass(Chat);
