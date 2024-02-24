import { Body, Controller, Post, UsePipes, ValidationPipe } from '@nestjs/common';
import { UserDto } from 'src/dto/socket/user.dto';
import { AuthService } from './auth.service';

@Controller('auth')
export class AuthController {
    constructor(private authServices :AuthService){}

    @Post(`/signUp`)
    signUpUser(@Body() cerateUserDto: UserDto) {
      return this.authServices.signUpUser(cerateUserDto);
    }

    @Post(`/signIn`)
    signInUser(@Body() cerateUserDto: UserDto) {
      return this.authServices.signInUser(cerateUserDto);
    }
    
}
