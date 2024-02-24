import { Injectable, UnauthorizedException } from '@nestjs/common';
import { PassportStrategy } from '@nestjs/passport';
import { Strategy, ExtractJwt } from 'passport-jwt';
import { ConfigService } from '@nestjs/config';
import { Model } from 'mongoose';
import { User } from 'src/schemas/user.schema';
import { InjectModel } from '@nestjs/mongoose';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy, 'jwt') {
  constructor(
    @InjectModel(User.name) private readonly userModel: Model<User>,
    private readonly configService: ConfigService,
  ) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      secretOrKey: configService.get('JWT_SECRET'),
    });
  }
  async validate(payload: { id: any; email: any }) {
    
    const { id, email } = payload;
    const user = await this.userModel.findById(id);
    if (!user && user.email!=email.toLowerCase()) {
      throw new UnauthorizedException('Authentication Failed');
    }
    
    const userObject = user.toObject();
    if (userObject.password) {
      delete userObject.password;
    }
    return userObject;
  }
}
