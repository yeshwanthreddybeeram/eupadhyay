import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IScheduleClass {
  id?: number;
  schedulelink?: string;
  scheduleTime?: Moment;
  studentname?: string;
  employeename?: string;
  videolink?: string;
  complete?: boolean;
  remove?: boolean;
  students?: IStudent[];
  employees?: IEmployee[];
}

export class ScheduleClass implements IScheduleClass {
  constructor(
    public id?: number,
    public schedulelink?: string,
    public scheduleTime?: Moment,
    public studentname?: string,
    public employeename?: string,
    public videolink?: string,
    public complete?: boolean,
    public remove?: boolean,
    public students?: IStudent[],
    public employees?: IEmployee[]
  ) {
    this.complete = this.complete || false;
    this.remove = this.remove || false;
  }
}
