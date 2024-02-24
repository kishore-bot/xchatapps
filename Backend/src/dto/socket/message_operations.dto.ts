import {IsNotEmpty, IsOptional, IsString, MinLength } from "class-validator";

export class MessageOperations {

  @IsOptional()
  @IsString()
  chatId: string;

  
  @IsOptional()
  @IsString()
  messageId: string;

  @IsOptional()
  userId:string
}
