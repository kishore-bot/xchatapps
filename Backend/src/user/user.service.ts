import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { AboutDto } from 'src/dto/api/about.dto';
import { ChatInfo } from 'src/dto/api/chatinfo.dto';
import { Chat } from 'src/schemas/chat.schema';
import { FriendRequest } from 'src/schemas/friend_reqest.schema';
import { User } from 'src/schemas/user.schema';

@Injectable()
export class UserService {
  constructor(
    @InjectModel(User.name) private userModel: Model<User>,
    @InjectModel(Chat.name) private chatModel: Model<Chat>,
    @InjectModel(FriendRequest.name) private reqModel: Model<FriendRequest>,
  ) {}

  async updateAbout(user: User, about: AboutDto) {
    const userId = user._id.toString();
    const me = await this.userModel.findById(userId);
    me.about = about.about;
    await me.save();
  }

  async createPersonalChat(user: User, id: string) {
    const rUser = id;
    const sUser = user._id;
    const [sendUser, recieveUser] = await Promise.all([
      this.userModel.findById(sUser).populate('chatList').exec(),
      this.userModel.findById(rUser).populate('chatList').exec(),
    ]);
    const sendUserChat = sendUser.chatList
      .filter((chat) => !chat.isGroup)
      .map((chat) => chat._id.toString());

    const recUserChat = recieveUser.chatList
      .filter((chat) => !chat.isGroup)
      .map((chat) => chat._id.toString());

    const alredyInChat = sendUserChat.filter((chatId) =>
      recUserChat.includes(chatId),
    );
    if (alredyInChat.length > 0) {
      return { message: 'Already you are in chat' };
    }
    const chat = await this.chatModel.create({
      owners: [sendUser._id, recieveUser._id],
    });
    sendUser.chatList.push(chat._id);
    recieveUser.chatList.push(chat._id);
    await sendUser.save();
    await recieveUser.save();
    return { message: 'SuccesFully Created' };
  }

  async fetchPChatInfo(chatInfo: ChatInfo) {
    const userId = chatInfo.userId;
    const chatId = chatInfo.chatId;
    const chat = await this.chatModel.findById(chatId);
    const otherOwnersIds = chat.owners.filter(
      (ownerId) => ownerId.toString() !== userId,
    );
    const temp = await this.userModel.findById(otherOwnersIds);
    const user = temp.toObject();
    delete user.friends;
    delete user.chatList;
    delete user.password;
    return user;
  }

  async searchUser(query: string, userDto: User) {
    const user = await this.userModel.findById(userDto._id);
    const results = await this.userModel.find(
      {
        username: { $regex: query, $options: 'i' }, // Case-insensitive regex search for usernames containing the query string
      },
      { username: 1, avatar: 1 },
    );
    const friends = user.friends;
    const preResult = results.filter((result) => {
      return !friends.some((friendId) => {
        return friendId.equals(result._id);
      });
    });

    const users = preResult.filter((result) => !user._id.equals(result._id));

    return { users };
  }

  async getAllFriend(userDto: User) {
    const friend = await this.userModel
      .findById(userDto._id)
      .populate({
        path: 'friends',
        model: 'User',
        select: 'username avatar',
      })
      .exec();

    return { users: friend.friends };
  }

  async requestUser(userDto: User, id: string) {
    const sendUser = await this.userModel.findById(userDto._id);
    const receiveUser = await this.userModel.findById(id);

    const isReqSend = sendUser.sentFriendRequests.find((send) => {
      return receiveUser.receivedFriendRequests.find((rec) => {
        return rec.equals(send);
      });
    });

    const isReqRec = sendUser.receivedFriendRequests.find((send) => {
      return receiveUser.sentFriendRequests.find((rec) => {
        return rec.equals(send);
      });
    });
    if (isReqSend !== undefined || isReqRec !== undefined) {
      if (isReqSend !== undefined) {
        return { message: 'Alreardy reqested' };
      }
      return { message: `${receiveUser.username} Alredy Requested` };
    }
    const request = await this.reqModel.create({
      senderId: userDto._id,
      receiverId: receiveUser._id,
    });

    sendUser.sentFriendRequests.push(request._id);
    receiveUser.receivedFriendRequests.push(request._id);

    await sendUser.save();
    await receiveUser.save();
    return { message: 'Requested' };
  }

  async fetchNotifications(userDto: User) {
    const user = await this.userModel.findById(userDto._id).populate({
      path: 'receivedFriendRequests',
      populate: {
        path: 'senderId',
        model: 'User',
        select: 'username avatar',
      },
    });
    const requests = user.receivedFriendRequests;
    return { requests };
  }

  async acceptUser(id: string) {
    const request = await this.reqModel.findById(id);
    if (request.isAccepted) {
      return { message: 'Already Accepted' };
    }
    const u1 = await this.userModel.findById(request.senderId);
    const u2 = await this.userModel.findById(request.receiverId);
    u1.friends.push(request.receiverId);
    u2.friends.push(request.senderId);
    await u1.save();
    await u2.save();
    request.isAccepted = true;
    request.save();
    return { message: `${u1.username} Request Accepted` };
  }
}
