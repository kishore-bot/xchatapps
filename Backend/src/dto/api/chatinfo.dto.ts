import { IsOptional, IsString } from 'class-validator';

export class ChatInfo {
  @IsString()
  @IsOptional()
  userId: string;

  @IsString()
  @IsOptional()
  chatId: string;
}
