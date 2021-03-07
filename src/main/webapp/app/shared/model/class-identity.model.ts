export interface IClassIdentity {
  id?: number;
  className?: string;
  description?: string;
}

export class ClassIdentity implements IClassIdentity {
  constructor(public id?: number, public className?: string, public description?: string) {}
}
