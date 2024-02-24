import { IsArray, IsNotEmpty, IsOptional, IsString } from 'class-validator';

export class GroupDto {
  @IsNotEmpty()
  @IsString()
  groupName: string;

  @IsOptional()
  @IsString()
  groupDesc: string;

  @IsArray()
  userId: { id: string }[];
}
