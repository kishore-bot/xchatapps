import { Module } from '@nestjs/common';
import { GroupController } from './group.controller';
import { GroupService } from './group.service';
import { MongooseModule } from '@nestjs/mongoose';
import { User, UserSchema } from 'src/schemas/user.schema';
import { Chat, ChatSchema } from 'src/schemas/chat.schema';
import { GroupDetails, GroupDetailsSchema } from 'src/schemas/group.schema';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema }, 
      { name: Chat.name, schema: ChatSchema },
      { name: GroupDetails.name, schema: GroupDetailsSchema },
    ]),
  ],
  controllers: [GroupController],
  providers: [GroupService]
})
export class GroupModule {}
