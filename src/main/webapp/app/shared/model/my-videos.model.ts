import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface IMyVideos {
  id?: number;
  videoLink?: string;
  conceptname?: string;
  scheduleTime?: Moment;
  students?: IStudent[];
}

export class MyVideos implements IMyVideos {
  constructor(
    public id?: number,
    public videoLink?: string,
    public conceptname?: string,
    public scheduleTime?: Moment,
    public students?: IStudent[]
  ) {}
}
