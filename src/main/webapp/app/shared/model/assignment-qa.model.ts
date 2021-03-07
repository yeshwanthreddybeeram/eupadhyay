export interface IAssignmentQA {
  id?: number;
  question?: string;
  answer?: string;
}

export class AssignmentQA implements IAssignmentQA {
  constructor(public id?: number, public question?: string, public answer?: string) {}
}
