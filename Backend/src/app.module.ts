import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { ChatGateWayModule } from './chat/chat.module';
import { MongooseModule } from '@nestjs/mongoose';
import { UserModule } from './user/user.module';
import { AuthModule } from './auth/auth.module';
import { FileModule } from './file/file.module';
import { GroupModule } from './group/group.module';
import { ScheduleModule } from '@nestjs/schedule';
import { TasksModule } from './tasks/tasks.module';


@Module({
  imports: [
    ScheduleModule.forRoot(),
    ConfigModule.forRoot({ isGlobal: true }),
    ChatGateWayModule,
    MongooseModule.forRootAsync({
      useFactory: (configService: ConfigService) => ({
        uri: configService.get<string>('CHAT_APP_DB_URL'),
      }),
      inject: [ConfigService],
    }),
    UserModule,
    AuthModule,
    FileModule,
    GroupModule,
    TasksModule,
  ],

  controllers: [],
  providers: [],
})
export class AppModule {}
