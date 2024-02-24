import { Injectable, PipeTransform } from "@nestjs/common";
import { MessageOperations } from "src/dto/socket/message_operations.dto";

@Injectable()
export class TransformMessageSeenPipe implements PipeTransform {
  transform(value: any): MessageOperations {
    if (typeof value === 'string') {
      return JSON.parse(value);
    } else if (typeof value === 'object') {
      return value
    } else {
      throw new Error('Invalid message format');
    }
  }
}
