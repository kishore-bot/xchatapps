import { Module } from '@nestjs/common';
import { FileController } from './file.controller';
import { FileService } from './file.service';
import { MongooseModule } from '@nestjs/mongoose';
import { User, UserSchema } from 'src/schemas/user.schema';
import { GroupDetails, GroupDetailsSchema } from 'src/schemas/group.schema';

@Module({
  imports:[
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema }, 
      { name: GroupDetails.name, schema: GroupDetailsSchema },
    ]),
  ],
  controllers: [FileController],
  providers: [FileService]
})
export class FileModule {}
