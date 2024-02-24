import { IsNotEmpty, IsOptional, IsString } from 'class-validator';

export class GroupOprns {
  @IsOptional()
  @IsString()
  about: string;

  @IsOptional()
  @IsString()
  name: string;

  @IsString()
  groupId: string;
}
