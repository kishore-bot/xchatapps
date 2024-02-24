import {
  CanActivate,
  ExecutionContext,
  Injectable,
} from '@nestjs/common';
import { Socket } from 'socket.io';
import { WsException } from '@nestjs/websockets';
import { AuthService } from 'src/auth/auth.service';
import { User } from 'src/schemas/user.schema';
import { ExtendedHandshake } from '../../decorator/extented-handshake';

@Injectable()
export class SocketioGuard implements CanActivate {
  constructor(private authService: AuthService) {}

  async canActivate(context: ExecutionContext): Promise<boolean> {
    const client: Socket = context.switchToWs().getClient<Socket>();
    if(context.getType()!='ws'){
      return true;
    }
    try {
      const { authorization } = client.handshake?.headers;
      if (!authorization) {
        client.emit('server-error', 'No authentication token provided');
        client.disconnect();
        return false;
      }

      const user: User = await this.authService.verifyUser(authorization.toString());

      if (!user) {
        client.emit('server-error', 'Invalid authentication token');
        client.disconnect();
        return false;
      }

      const extendedHandshake = client.handshake as ExtendedHandshake;
      const userObject = user.toObject();
      delete userObject.password;
      extendedHandshake.user = userObject;
      return true;
    } catch (err) {
      const errorMessage = 'An error occurred during authentication';
      client.emit('server-error', errorMessage);
      client.disconnect();
      throw new WsException(errorMessage);
    }
  }
}