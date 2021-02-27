import { Moment } from 'moment';
import { IDepartment } from 'app/shared/model/department.model';
import { IScheduleClass } from 'app/shared/model/schedule-class.model';

export interface IEmployee {
  id?: number;
  username?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  salary?: number;
  departments?: IDepartment[];
  scheduleClasses?: IScheduleClass[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public username?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public hireDate?: Moment,
    public salary?: number,
    public departments?: IDepartment[],
    public scheduleClasses?: IScheduleClass[]
  ) {}
}
