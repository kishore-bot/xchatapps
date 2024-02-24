import { User } from "src/schemas/user.schema";

export interface ExtendedHandshake {
    headers: { authorization: string };
    user?: User;
  }