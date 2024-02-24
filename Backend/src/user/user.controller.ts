import {
  Body,
  Controller,
  Get,
  Param,
  Patch,
  Post,
  Query,
  UseGuards,
} from '@nestjs/common';
import { UserService } from './user.service';
import { User } from 'src/schemas/user.schema';
import { JwtGuard } from 'src/auth/guard/jwt.guard';
import { GetUser } from 'src/auth/decorator/get_user.decorator';
import { GroupDto } from 'src/dto/socket/group.dto';
import { AboutDto } from 'src/dto/api/about.dto';
import { ChatInfo } from 'src/dto/api/chatinfo.dto';
import { GroupOprns } from 'src/dto/api/group_oprn.dto';

@UseGuards(JwtGuard)
@Controller('user')
export class UserController {
  constructor(private userService: UserService) {}

  @Get('/me')
  async fetchMe(@GetUser() user: User) {
    try {
      return user;
    } catch (error) {
      console.error(error);
      throw new Error('Failed to fetch users');
    }
  }

  @Post('/about')
  async updateAbout(@GetUser() user: User, @Body() about: AboutDto) {
    await this.userService.updateAbout(user, about);
  }

  @Post('/p_chat')
  async fetchPChatInfo(@Body() chatInfo: ChatInfo) {
    return await this.userService.fetchPChatInfo(chatInfo);
  }

  @Post('/personal/:userId')
  createPersonal(@GetUser() user: User, @Param('userId') userId: string) {
    return this.userService.createPersonalChat(user, userId);
  }

  @Post('/search')
  async searchUser(@Query('user') query: string, @GetUser() user: User) {
    return await this.userService.searchUser(query, user);
  }

  @Get('/friends')
  async getAllFriens(@GetUser() user: User) {
    return await this.userService.getAllFriend(user);
  }

  @Post('/request/:id')
  async requestUser(@GetUser() user: User, @Param('id') id: string) {
    return await this.userService.requestUser(user, id);
  }
  @Get('/received/req')
  async fetchNotifications(@GetUser() user: User) {
    return await this.userService.fetchNotifications(user);
  }
  @Post('/accept/:id')
  async acceptUser(@Param('id') id: string) {
    return await this.userService.acceptUser(id);
  }
}
