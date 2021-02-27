import { IConcept } from 'app/shared/model/concept.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IDepartment {
  id?: number;
  departmentName?: string;
  concepts?: IConcept[];
  employees?: IEmployee[];
}

export class Department implements IDepartment {
  constructor(public id?: number, public departmentName?: string, public concepts?: IConcept[], public employees?: IEmployee[]) {}
}
