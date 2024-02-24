import { Module } from '@nestjs/common';
import { TasksService } from './tasks.service';
import { MongooseModule } from '@nestjs/mongoose';
import { ConfigModule } from '@nestjs/config';
import { Chat, ChatSchema } from 'src/schemas/chat.schema';
import { Messages, MessagesSchema } from 'src/schemas/message.schema';
import { User, UserSchema } from 'src/schemas/user.schema';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema },
      { name: Chat.name, schema: ChatSchema },
      { name: Messages.name, schema: MessagesSchema },
    ]),
    ConfigModule.forRoot(),
  ],
  providers: [TasksService]
})
export class TasksModule {}
