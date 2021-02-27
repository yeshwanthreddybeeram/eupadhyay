import { Moment } from 'moment';
import { IMyVideos } from 'app/shared/model/my-videos.model';
import { IScheduleClass } from 'app/shared/model/schedule-class.model';

export interface IStudent {
  id?: number;
  userName?: string;
  fullName?: string;
  email?: string;
  phoneNumber?: string;
  joindate?: Moment;
  myVideos?: IMyVideos[];
  scheduleClasses?: IScheduleClass[];
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public userName?: string,
    public fullName?: string,
    public email?: string,
    public phoneNumber?: string,
    public joindate?: Moment,
    public myVideos?: IMyVideos[],
    public scheduleClasses?: IScheduleClass[]
  ) {}
}
