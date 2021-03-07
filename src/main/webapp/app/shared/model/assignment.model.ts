import { Moment } from 'moment';
import { Assignmentstatus } from 'app/shared/model/enumerations/assignmentstatus.model';

export interface IAssignment {
  id?: number;
  subject?: string;
  description?: string;
  studentloginname?: string;
  employeeloginname?: string;
  status?: string;
  submitdate?: Moment;
  marks?: number;
  remarks?: string;
  asgnstatus?: Assignmentstatus;
  assignmentlink?: string;
  submitlink?: string;
  assignmentpdfContentType?: string;
  assignmentpdf?: any;
  submitpdfContentType?: string;
  submitpdf?: any;
}

export class Assignment implements IAssignment {
  constructor(
    public id?: number,
    public subject?: string,
    public description?: string,
    public studentloginname?: string,
    public employeeloginname?: string,
    public status?: string,
    public submitdate?: Moment,
    public marks?: number,
    public remarks?: string,
    public asgnstatus?: Assignmentstatus,
    public assignmentlink?: string,
    public submitlink?: string,
    public assignmentpdfContentType?: string,
    public assignmentpdf?: any,
    public submitpdfContentType?: string,
    public submitpdf?: any
  ) {}
}
