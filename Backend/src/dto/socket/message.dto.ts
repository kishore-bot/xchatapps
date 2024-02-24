import {
  IsBoolean,
  IsNotEmpty,
  IsOptional,
  IsString,
  MinLength,
} from 'class-validator';
import { Transform } from 'class-transformer';

export class MessageDto {
  @IsString()
  @IsNotEmpty()
  chatId: string;

  @IsString()
  @MinLength(1)
  message: string;

  @IsString()
  @IsOptional()
  replayId: string;

  @IsBoolean()
  @Transform(({ value }) => (value !== undefined ? value : false))
  isFile: boolean;
}
