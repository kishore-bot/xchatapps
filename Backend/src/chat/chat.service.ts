import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { User } from 'src/schemas/user.schema';
import { Model } from 'mongoose';
import { Socket } from 'socket.io';
import { Chat } from 'src/schemas/chat.schema';

@Injectable()
export class ChatService {
  constructor(@InjectModel(Chat.name) private chatModel: Model<Chat>) {}

  async handleConnection(
    user: User,
    client: Socket,
    socketIdMap: Map<string, string>,
  ): Promise<void> {
    const userId = user._id.toString();
    socketIdMap.set(userId, client.id);
    for (const chatId of user.chatList) {
      const chatQuery = await this.chatModel.findById(chatId);
      if (chatQuery) {
        const populatedChat = await chatQuery.populate('messages');
        for (const message of populatedChat.messages) {
          if (
            message.sender.toString() !== userId &&
            message.status === 'sent'
          ) {
            message.status = 'received';
            await message.save();
          }
        }
      }
    }
  }

  async handleDisconnect(userDto: User, socketIdMap: Map<string, string>) {
    const userId = userDto._id.toString();
    socketIdMap.delete(userId);
  }

  async getChatList(userDto: User): Promise<
    {
      chatId: string;
      name: string;
      userId: string;
      avatar: string;
      isGroup: boolean;
      lastMessage: any;
    }[]
  > {
    const userId = userDto._id.toString();

    // Fetch chats
    const chatList = await this.getPopulatedChats(userDto);

    // Transform chats
    return chatList.map((chat) => this.transformChat(userId, chat));
  }

  private async getPopulatedChats(userDto: User) {
    return await this.chatModel
      .find({
        _id: { $in: userDto.chatList },
      })
      .populate({
        path: 'messages',
        model: 'Messages',
        populate: {
          path: 'sender',
          model: 'User',
          select: 'username _id',
        },
      })
      .populate({
        path: 'owners',
        model: 'User',
        select: 'username avatar',
      })
      .populate({
        path: 'group',
        model: 'GroupDetails',
        select: 'groupName avatar',
      })
      .sort({ updatedAt: -1 });
  }

  private transformChat(userId: string, chat: any) {
    let name: string;
    let avatar: string;
    if (!chat.isGroup) {
      const owner = chat.owners.find(
        (owner: any) => String(owner._id) !== userId,
      );
      avatar = owner?.avatar ?? '';
      name = owner ? owner.username : '';
    } else {
      name = chat.group.groupName;
      avatar = chat.group.avatar ?? '';
    }

    const isGroup = chat.isGroup;
    const chatId = chat._id.toString();
    const lastMessage = this.getLastMessage(chat);

    return {
      chatId,
      name,
      userId,
      avatar,
      isGroup,
      lastMessage,
    };
  }

  private getLastMessage(chat: any) {
    if (chat.messages.length > 0) {
      let lastMessage = {
        ...chat.messages[chat.messages.length - 1].toObject(),
      };
      lastMessage.sender = {
        username: chat.messages[chat.messages.length - 1].sender.username,
        _id: chat.messages[chat.messages.length - 1].sender._id,
      };
      if (lastMessage.replay) {
        delete lastMessage.replay;
      }

      return lastMessage;
    } else {
      return null;
    }
  }
}

// async getChatList(
//   userDto: User,
// ): Promise<{ name: string; lastMessage: any }[]> {
//   const userId = userDto._id.toString();
//   const chatList = await this.chatModel
//     .find({
//       _id: { $in: userDto.chatList },
//     })
//     .populate({
//       path: 'messages',
//       model: 'Messages',
//       populate: {
//         path: 'sender',
//         model: 'User',
//         select: 'username _id',
//       },
//     })
//     .populate({
//       path: 'owners',
//       model: 'User',
//       select: 'username',
//     })
//     .populate({
//       path: 'group',
//       model: 'GroupDetails',
//       select: 'groupName',
//     })
//     .sort({ updatedAt: -1 });

//   return chatList.map((chat) => {
//     let name: string;
//     if (!chat.isGroup) {
//       const owner = chat.owners.find((owner) => String(owner._id) !== userId);
//       name = owner ? owner.username : '';
//     } else {
//       name = chat.group.groupName;
//     }
//     const isGraph = chat.isGroup;
//     const chatId = chat._id.toString();
//     const lastMessage =
//       chat.messages.length > 0
//         ? {
//             ...chat.messages[chat.messages.length - 1].toObject(),
//             sender: {
//               username:
//                 chat.messages[chat.messages.length - 1].sender.username,
//               _id: chat.messages[chat.messages.length - 1].sender._id,
//             },
//           }
//         : null;
//     if (lastMessage && lastMessage.replay) {
//       delete lastMessage.replay;
//     }
//     return {
//       chatId,
//       name,
//       userId,
//       isGraph,
//       lastMessage,
//     };
//   });
// }
