import { IVideoLink } from 'app/shared/model/video-link.model';
import { IDepartment } from 'app/shared/model/department.model';

export interface IConcept {
  id?: number;
  conceptname?: string;
  videoLinks?: IVideoLink[];
  departments?: IDepartment[];
}

export class Concept implements IConcept {
  constructor(public id?: number, public conceptname?: string, public videoLinks?: IVideoLink[], public departments?: IDepartment[]) {}
}
