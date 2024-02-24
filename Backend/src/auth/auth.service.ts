import { ConflictException, HttpException, Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { User } from 'src/schemas/user.schema';
import { UserDto } from 'src/dto/socket/user.dto';
import { JwtService } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
const bcrypt = require('bcrypt');

@Injectable()
export class AuthService {
  constructor(
    @InjectModel(User.name)
    private userModel: Model<User>,

    private jwtServices: JwtService,

    private config: ConfigService,
  ) {}

  async signUpUser(createUserDto: UserDto) {
    const existingUser = await this.userModel.findOne({
      email: createUserDto.email,
    });

    if (existingUser) {
      throw new ConflictException('Email already exists');
    }
    const hashedPassword = await bcrypt.hash(createUserDto.password, 8);
    const newUser = await this.userModel.create({
      username: createUserDto.username,
      email: createUserDto.email,
      password: hashedPassword,
      phoneNo: createUserDto.phoneNo,
    });

    const token = await this.createToken(createUserDto.email, newUser.id);
    return { token };
  }

  async signInUser(createUserDto: UserDto) {
    const existingUser = await this.userModel.findOne({
      email: createUserDto.email,
    });

    if (!existingUser) {
      throw new HttpException('Email or Password Failed', 404);
    }
    const isMatch = await bcrypt.compare(
      createUserDto.password,
      existingUser.password,
    );
    if (!isMatch) {
      throw new HttpException('Email or Password Failed', 404);
    }
    const token = await this.createToken(createUserDto.email, existingUser.id);
    return { token };
  }

  async createToken(email: String, id: String) {
    const payload = {
      id,
      email,
    };
    const secret = this.config.get('JWT_SECRET');
    const token = await this.jwtServices.signAsync(payload, { secret });
    // const decoded = await this.jwtServices.verifyAsync(token, { secret });
    return token;
  }

  async verifyUser(token: string): Promise<User | null> {
    try {
      const authToken = token.replace('Bearer ', '');
      const decoded = await this.jwtServices.verifyAsync(authToken, {
        secret: this.config.get('JWT_SECRET'),
      });

      const user = await this.userModel.findOne({
        _id: decoded.id,
        email: decoded.email.toLowerCase(),
      });

      return user;
    } catch (error) {
      throw new HttpException('Invalid token', 401);
    }
  }
}
