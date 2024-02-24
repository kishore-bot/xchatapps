import {
  Controller,
  FileTypeValidator,
  MaxFileSizeValidator,
  Param,
  ParseFilePipe,
  Post,
  UploadedFile,
  UseGuards,
  UseInterceptors,
} from '@nestjs/common';
import { JwtGuard } from 'src/auth/guard/jwt.guard';
import { FileService } from './file.service';
import { FileInterceptor } from '@nestjs/platform-express';
import { GetUser } from 'src/auth/decorator/get_user.decorator';
import { User } from 'src/schemas/user.schema';

@UseGuards(JwtGuard)
@Controller('file')
export class FileController {
  constructor(private readonly fileService: FileService) {}

  @Post('/profile')
  @UseInterceptors(FileInterceptor('picture'))
  async uploadFile(
    @GetUser() UserDto: User,
    @UploadedFile(
      new ParseFilePipe({
        validators: [
          new MaxFileSizeValidator({ maxSize: 5 * 1024 * 1024 }),
          new FileTypeValidator({ fileType: /image\/(jpeg|png)/ }),
        ],
      }),
    )
    file: Express.Multer.File,
  ) {
    const url = await this.fileService.uploadProfile(UserDto, file);
    return { url };
  }

  @Post('/chat')
  @UseInterceptors(FileInterceptor('picture'))
  async chatImageUpload(
    @UploadedFile(
      new ParseFilePipe({
        validators: [
          new MaxFileSizeValidator({ maxSize: 5 * 1024 * 1024 }),
          new FileTypeValidator({ fileType: /image\/(jpeg|png)/ }),
        ],
      }),
    )
    file: Express.Multer.File,
  ) {
    const url = await this.fileService.chatImageUpload(file);
    return { url };
  }

  @Post('/group/:id')
  @UseInterceptors(FileInterceptor('picture'))
  async groupImageUpload(
    @Param('id') id: string,
    @UploadedFile(
      new ParseFilePipe({
        validators: [
          new MaxFileSizeValidator({ maxSize: 5 * 1024 * 1024 }),
          new FileTypeValidator({ fileType: /image\/(jpeg|png)/ }),
        ],
      }),
    )
    file: Express.Multer.File,
  ) {
    const url = await this.fileService.uploadGroup(id, file);
    return { url};
  }
}
