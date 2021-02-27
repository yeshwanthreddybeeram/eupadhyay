export interface IAssignment {
  id?: number;
  subject?: string;
  description?: string;
}

export class Assignment implements IAssignment {
  constructor(public id?: number, public subject?: string, public description?: string) {}
}
