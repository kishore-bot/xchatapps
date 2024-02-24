import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { User, UserSchema } from 'src/schemas/user.schema';
import { ChatGateWay } from './chat.gateway';
import { ChatService } from './chat.service';
import { JwtModule } from '@nestjs/jwt';
import { ConfigModule } from '@nestjs/config';
import { AuthService } from 'src/auth/auth.service';
import { SocketioAuthMiddleware } from 'src/auth/middleware/socketio.middleware';
import { Chat, ChatSchema } from 'src/schemas/chat.schema';
import { TransformMessageDtoPipe } from 'src/utils/message-dto/message-dto.pipe';
import { TransformMessageSeenPipe } from 'src/utils/message-dto/transform_message_seen.pipe';
import { MessagesService } from './messages/messages.service';
import { Messages, MessagesSchema } from 'src/schemas/message.schema';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema },
      { name: Chat.name, schema: ChatSchema },
      { name: Messages.name, schema: MessagesSchema },
    ]),
    JwtModule.register({}),
    ConfigModule.forRoot(),
  ],
  providers: [
    ChatGateWay,
    ChatService,
    AuthService,
    SocketioAuthMiddleware,
    TransformMessageDtoPipe,
    TransformMessageSeenPipe,
    MessagesService,
  ],
})
export class ChatGateWayModule {}
