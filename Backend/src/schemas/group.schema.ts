import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import mongoose, { Document } from 'mongoose';
import { User } from './user.schema';

@Schema()
export class GroupDetails extends Document {
  @Prop({ type: String })
  groupName: string;

  @Prop({ type: Date, default: Date.now })
  createdAt: Date;

  @Prop({ type: String })
  groupDescription?: string;

  @Prop({ type: mongoose.Schema.Types.ObjectId, ref: 'User' })
  admin: User;

  @Prop({ type: String })
  avatar?: string;
}

export const GroupDetailsSchema = SchemaFactory.createForClass(GroupDetails);
