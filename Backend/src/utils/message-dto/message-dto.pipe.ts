import { PipeTransform, Injectable, BadRequestException } from '@nestjs/common';
import { MessageDto } from 'src/dto/socket/message.dto';

@Injectable()
export class TransformMessageDtoPipe implements PipeTransform {
  transform(value: any): MessageDto {
    try {
      if (typeof value === 'string') {
        return JSON.parse(value);
      } else if (typeof value === 'object') {
        return value; // No need to parse, it's already an object
      } else {
        throw new BadRequestException('Invalid message format');
      }
    } catch (error) {
      throw new BadRequestException('Invalid JSON format');
    }
  }
}
