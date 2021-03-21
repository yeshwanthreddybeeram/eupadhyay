export interface IGuest {
  id?: number;
  name?: string;
  email?: string;
  mobilenumber?: string;
  subject?: string;
  message?: string;
  reply?: string;
}

export class Guest implements IGuest {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public mobilenumber?: string,
    public subject?: string,
    public message?: string,
    public reply?: string
  ) {}
}
