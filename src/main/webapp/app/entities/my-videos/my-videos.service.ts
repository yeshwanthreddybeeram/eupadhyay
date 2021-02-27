import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMyVideos } from 'app/shared/model/my-videos.model';

type EntityResponseType = HttpResponse<IMyVideos>;
type EntityArrayResponseType = HttpResponse<IMyVideos[]>;

@Injectable({ providedIn: 'root' })
export class MyVideosService {
  public resourceUrl = SERVER_API_URL + 'api/my-videos';

  constructor(protected http: HttpClient) {}

  create(myVideos: IMyVideos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(myVideos);
    return this.http
      .post<IMyVideos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(myVideos: IMyVideos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(myVideos);
    return this.http
      .put<IMyVideos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMyVideos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMyVideos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(myVideos: IMyVideos): IMyVideos {
    const copy: IMyVideos = Object.assign({}, myVideos, {
      scheduleTime: myVideos.scheduleTime && myVideos.scheduleTime.isValid() ? myVideos.scheduleTime.toJSON() : undefined,
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
      res.body.forEach((myVideos: IMyVideos) => {
        myVideos.scheduleTime = myVideos.scheduleTime ? moment(myVideos.scheduleTime) : undefined;
      });
    }
    return res;
  }
}
