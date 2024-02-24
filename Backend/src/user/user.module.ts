import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { User, UserSchema } from 'src/schemas/user.schema';
import { UserService } from './user.service';
import { UserController } from './user.controller';
import { Chat, ChatSchema } from 'src/schemas/chat.schema';
import { FriendRequest, FriendRequestSchema } from 'src/schemas/friend_reqest.schema';



@Module({
  imports: [
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema }, 
      { name: Chat.name, schema: ChatSchema },
      { name: FriendRequest.name, schema: FriendRequestSchema },
    ]),
  ],
  providers: [UserService],
  controllers: [UserController],
})
export class UserModule {}
