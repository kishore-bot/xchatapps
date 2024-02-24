import { Body, Controller, Patch, Post, UseGuards } from '@nestjs/common';
import { GetUser } from 'src/auth/decorator/get_user.decorator';
import { JwtGuard } from 'src/auth/guard/jwt.guard';
import { ChatInfo } from 'src/dto/api/chatinfo.dto';
import { GroupOprns } from 'src/dto/api/group_oprn.dto';
import { GroupDto } from 'src/dto/socket/group.dto';
import { User } from 'src/schemas/user.schema';
import { GroupService } from './group.service';

@UseGuards(JwtGuard)
@Controller('group')
export class GroupController {
  constructor(private readonly groupServices: GroupService) {}

  @Post('/create')
  createGroup(@Body() groupDto: GroupDto, @GetUser() user: User) {
    return this.groupServices.createGroup(user, groupDto);
  }

  @Post('/description')
  updateGroupAbout(@Body() grp: GroupOprns) {
    return this.groupServices.updateGroupAbout(grp);
  }

  @Post('/name')
  updateGroupName(@Body() grp: GroupOprns) {
    return this.groupServices.updateGroupName(grp);
  }

  @Patch('/leave')
  async leaveFromGroup(@GetUser() user: User, @Body() chatInfo: ChatInfo) {
    return await this.groupServices.leaveFromGroup(user, chatInfo);
  }

  @Patch('/remove')
  async removeFromGroup(@Body() chatInfo: ChatInfo) {
    return await this.groupServices.removeFromGroup(chatInfo);
  }
  @Post('/members')
  async addMembersToGroup(@Body() groupDto: GroupDto) {
    return await this.groupServices.addMembersToGroup(groupDto);
  }
  @Post('/groupInfo')
  async fetchGChatInfo(@Body() chatInfo: ChatInfo) {
    return await this.groupServices.fetchGChatInfo(chatInfo);
  }

}
