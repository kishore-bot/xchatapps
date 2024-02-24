import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import mongoose, { Document, Schema as MongooseSchema } from 'mongoose';
import { User } from './user.schema';
import { Chat } from './chat.schema';

@Schema()
export class Messages extends Document {
  @Prop()
  content: string;

  @Prop({ type: Date, default: Date.now })
  createdAt: Date;

  @Prop({ type: mongoose.Schema.Types.ObjectId, ref: 'User' })
  sender: User;

  @Prop({ type: mongoose.Schema.Types.ObjectId, ref: 'Chat' })
  chatId: Chat;

  @Prop({
    status: {
      type: String,
      enum: ['sent', 'received', 'seen'],
    },
  })
  status: 'sent' | 'received' | 'seen';

  @Prop({ type: Boolean, default: false })
  saved: boolean;

  @Prop({ type: mongoose.Schema.Types.ObjectId, ref: 'Messages' })
  replay?: mongoose.Types.ObjectId;

  @Prop({ type: Boolean, default: false })
  isFile: boolean;
}
export const MessagesSchema = SchemaFactory.createForClass(Messages);
