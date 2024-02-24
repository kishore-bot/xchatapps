import { Injectable } from '@nestjs/common';
import { WsException } from '@nestjs/websockets';
import { AuthService } from '../auth.service';
import { User } from 'src/schemas/user.schema';
import { Socket } from 'socket.io';
import { ExtendedHandshake } from '../decorator/extented-handshake';


@Injectable()
export class SocketioAuthMiddleware {
  constructor(private authService: AuthService) {}

  async use(client: Socket, next: () => void): Promise<void> {
    try {
      const { authorization } = client.handshake?.headers;
      if (!authorization) {
        client.emit('server-error'," error.message");
        client.disconnect(true); 
      }

      const user: User = await this.authService.verifyUser(authorization.toString());

      if (!user) {
        throw new WsException('Invalid authentication token');
      }

      const extendedHandshake = client.handshake as ExtendedHandshake;
      const userObject = user.toObject();
      delete userObject.password;
      extendedHandshake.user = userObject;
      next();
    } catch (error) {
      client.emit('server-error', error.message);
      client.disconnect(true);
      
    }
  }
}