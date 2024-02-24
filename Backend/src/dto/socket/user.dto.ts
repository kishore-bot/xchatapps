import {
  IsEmail,
  IsNumber,
  IsOptional,
  IsString,
  MinLength,
} from 'class-validator';

export class UserDto {
  @IsString()
  @IsOptional()
  username: string;

  @IsString()
  @IsEmail()
  email: string;

  @IsString()
  @MinLength(6)
  password: string;

  @IsOptional()
  @IsString()
  id?: string;

  @IsOptional()
  @IsNumber()
  phoneNo: number;
}
