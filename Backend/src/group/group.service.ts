import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import mongoose, { Model } from 'mongoose';
import { ChatInfo } from 'src/dto/api/chatinfo.dto';
import { GroupOprns } from 'src/dto/api/group_oprn.dto';
import { GroupDto } from 'src/dto/socket/group.dto';
import { User } from 'src/schemas/user.schema';
import { Chat } from 'src/schemas/chat.schema';
import { GroupDetails } from 'src/schemas/group.schema';

@Injectable()
export class GroupService {
  constructor(
    @InjectModel(User.name) private userModel: Model<User>,
    @InjectModel(Chat.name) private chatModel: Model<Chat>,
    @InjectModel(GroupDetails.name) private group: Model<GroupDetails>,
  ) {}

  async createGroup(user: User, groupDto: GroupDto) {
    const userID = user._id.toString();
    const group = await this.group.create({
      groupName: groupDto.groupName,
      groupDescription: groupDto.groupDesc ? groupDto.groupDesc : '',
      admin: userID,
    });
    const userIds = groupDto.userId.map((userIdDto) => userIdDto.id); // Extracting userIds
    const ownerIds = [
      userID,
      ...userIds.map((id) => new mongoose.Types.ObjectId(id)),
    ];
    const chat = await this.chatModel.create({
      group: group._id,
      isGroup: true,
      owners: ownerIds,
    });
    const creater = await this.userModel.findById(user._id);
    creater.chatList.push(chat._id);
    await creater.save();
    for (const userId of userIds) {
      const user = await this.userModel.findById(userId);
      user.chatList.push(chat._id);
      await user.save();
    }
    return { message: `SuccessFully created ${groupDto.groupName}` };
  }

  async fetchGChatInfo(chatInfo: ChatInfo) {
    const chatId = chatInfo.chatId;
    const chatModel = await this.chatModel
      .findById(chatId)
      .populate({
        path: 'group',
        model: 'GroupDetails',
      })
      .populate({
        path: 'owners',
        model: 'User',
        select: 'username',
      });
    const chat = chatModel.toObject();
    delete chat.createdAt;
    delete chat.messages;
    delete chat.updatedAt;
    delete chat.isGroup;
    return chat;
  }

  async updateGroupAbout(grp: GroupOprns) {
    const group = await this.group.findById(grp.groupId);
    group.groupDescription = grp.about;
    group.save();
    return;
  }

  async updateGroupName(grp: GroupOprns) {
    const group = await this.group.findById(grp.groupId);
    group.groupName = grp.name;
    group.save();
    return;
  }

  async leaveFromGroup(userDto: User, chatInfo: ChatInfo) {
    const userId = userDto._id.toString();
    const user = await this.userModel.findById(userId);
    const chat = await this.chatModel.findById(chatInfo.chatId);
    const index = user.chatList.findIndex((chatId: any) =>
      chatId.equals(chatInfo.chatId),
    );
    if (index !== -1) {
      user.chatList.splice(index, 1);
      await user.save();
    }
    const chatIndex = chat.owners.findIndex((user: any) => user.equals(userId));
    if (chatIndex !== -1) {
      chat.owners.splice(chatIndex, 1);
      await chat.save();
    }
    if (chat.owners.length == 0) {
      const grp = chat.group;
      await this.group.deleteOne({ _id: grp });
      await this.chatModel.deleteOne({ _id: chat._id });
    }
    return { message: 'Success' };
  }

  async removeFromGroup(chatInfo: ChatInfo) {
    const user = await this.userModel.findById(chatInfo.userId);
    const index = user.chatList.findIndex((chatId: any) =>
      chatId.equals(chatInfo.chatId),
    );
    if (index !== -1) {
      user.chatList.splice(index, 1);
      await user.save();
    }
    const chat = await this.chatModel.findById(chatInfo.chatId);
    const chatIndex = chat.owners.findIndex((user: any) =>
      user.equals(chatInfo.userId),
    );
    if (chatIndex !== -1) {
      chat.owners.splice(chatIndex, 1);
      await chat.save();
    }
    return { message: chatInfo.userId };
  }

  async addMembersToGroup(groupDto: GroupDto) {
    const chat = await this.chatModel.findById(groupDto.groupName);
    const ownerIdSet = new Set(chat.owners.map((owner) => owner.toString()));

    const filteredUserIds = groupDto.userId.filter(
      (user) => !ownerIdSet.has(user.id),
    );

    if (filteredUserIds.length === 0) {
      return { message: 'They are already present' };
    }
    const users = [];

    for (const userId of filteredUserIds) {
      const user = await this.userModel.findById(userId.id);
      users.push(user);
    }
    for (const user of users) {
      chat.owners.push(user._id);
      user.chatList.push(chat._id);
      await user.save();
    }
    await chat.save();
    return { message: 'Sucess Added' };
  }
}
