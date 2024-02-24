import { UseGuards, UsePipes } from '@nestjs/common';
import {
  WebSocketGateway,
  WebSocketServer,
  SubscribeMessage,
  ConnectedSocket,
  MessageBody,
} from '@nestjs/websockets';
import { ChatService } from './chat.service';
import { SocketioAuthMiddleware } from 'src/auth/middleware/socketio.middleware';
import { SocketioGuard } from 'src/auth/guard/socketio/socketio.guard';
import { Server, Socket } from 'socket.io';
import { ExtendedHandshake } from 'src/auth/decorator/extented-handshake';
import { TransformMessageDtoPipe } from 'src/utils/message-dto/message-dto.pipe';
import { MessagesService } from './messages/messages.service';
import { MessageOperations } from 'src/dto/socket/message_operations.dto';
import { MessageDto } from 'src/dto/socket/message.dto';


@WebSocketGateway(3001, { namespace: 'chat' })
@UseGuards(SocketioGuard)
export class ChatGateWay {
  constructor(
    private chatServices: ChatService,
    private socketMiddileware: SocketioAuthMiddleware,
    private messagesService: MessagesService,
  ) {}

  @WebSocketServer()
  server: Server;
  async afterInit(server: Server) {
    server.use(async (client: Socket, next) => {
      try {
        await this.socketMiddileware.use(client, () => next());
      } catch (error) {
        client.emit('server-error', error.message);
        client.disconnect(true);
      }
    });
  }

  private socketIdMap: Map<string, string> = new Map<string, string>();

  @SubscribeMessage('connection')
  async handleConnection(@ConnectedSocket() client: Socket) {
    const extendedHandshake = client.handshake as ExtendedHandshake;
    const user = extendedHandshake.user;
    await this.chatServices.handleConnection(user, client, this.socketIdMap);
    return 'Successfully Connected';
  }

  @SubscribeMessage('get-chat-list')
  async handleGetChatList(@ConnectedSocket() client: Socket) {
    const extendedHandshake = client.handshake as ExtendedHandshake;
    const user = extendedHandshake.user;
    const chat = await this.chatServices.getChatList(user);
    return { chat };
  }

  @SubscribeMessage('get-chat')
  @UsePipes(TransformMessageDtoPipe)
  async handleGetChat(@MessageBody() messageDto: MessageOperations) {
    const chat = await this.messagesService.getChat(messageDto);
    return { chat };
  }

  @SubscribeMessage('send-message')
  @UsePipes(TransformMessageDtoPipe)
  async sendMessageToServer(
    @ConnectedSocket() client: Socket,
    @MessageBody() messageDto: MessageDto,
  ) {
    const extendedHandshake = client.handshake as ExtendedHandshake;
    const user = extendedHandshake.user;
    const { receiverSockId, message } = await this.messagesService.sendMessage(
      messageDto,
      user,
      this.socketIdMap,
    );
    client.to(receiverSockId).emit('receive-message', message);
    return message;
  }

  @SubscribeMessage('seen-message')
  @UsePipes(TransformMessageDtoPipe)
  async makeAsSeen(@MessageBody() messageDto: MessageOperations) {
    const { socketId, messages } = await this.messagesService.seenMessage(
      messageDto,
      this.socketIdMap,
    );
    this.server.to(socketId).emit('seen-message', messages);
  }

  @SubscribeMessage('save-message')
  @UsePipes(TransformMessageDtoPipe)
  async isSave(@MessageBody() messageDto: MessageOperations) {
    const save = await this.messagesService.isSave(messageDto);
    return save;
  }

  @SubscribeMessage('unsent-message')
  @UsePipes(TransformMessageDtoPipe)
  async unSend(@MessageBody() messageDto: MessageOperations) {
    const { message, socketId } = await this.messagesService.unSend(
      messageDto,
      this.socketIdMap,
    );
    this.server.to(socketId).emit('unsent-message', message);
    return message;
  }

  async handleDisconnect(@ConnectedSocket() client: Socket) {
    const extendedHandshake = client.handshake as ExtendedHandshake;
    const user = extendedHandshake.user;
    this.chatServices.handleDisconnect(user, this.socketIdMap);
    return;
  }
}
