import { IsNotEmpty, IsString } from "class-validator";

export class AboutDto {
  @IsNotEmpty()
  @IsString()
  about: string;
}
