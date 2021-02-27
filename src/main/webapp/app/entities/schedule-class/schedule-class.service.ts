import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScheduleClass } from 'app/shared/model/schedule-class.model';

type EntityResponseType = HttpResponse<IScheduleClass>;
type EntityArrayResponseType = HttpResponse<IScheduleClass[]>;

@Injectable({ providedIn: 'root' })
export class ScheduleClassService {
  public resourceUrl = SERVER_API_URL + 'api/schedule-classes';

  constructor(protected http: HttpClient) {}

  create(scheduleClass: IScheduleClass): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scheduleClass);
    return this.http
      .post<IScheduleClass>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(scheduleClass: IScheduleClass): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scheduleClass);
    return this.http
      .put<IScheduleClass>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IScheduleClass>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScheduleClass[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(scheduleClass: IScheduleClass): IScheduleClass {
    const copy: IScheduleClass = Object.assign({}, scheduleClass, {
      scheduleTime: scheduleClass.scheduleTime && scheduleClass.scheduleTime.isValid() ? scheduleClass.scheduleTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.scheduleTime = res.body.scheduleTime ? moment(res.body.scheduleTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((scheduleClass: IScheduleClass) => {
        scheduleClass.scheduleTime = scheduleClass.scheduleTime ? moment(scheduleClass.scheduleTime) : undefined;
      });
    }
    return res;
  }
}
