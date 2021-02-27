import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVideoLink } from 'app/shared/model/video-link.model';

type EntityResponseType = HttpResponse<IVideoLink>;
type EntityArrayResponseType = HttpResponse<IVideoLink[]>;

@Injectable({ providedIn: 'root' })
export class VideoLinkService {
  public resourceUrl = SERVER_API_URL + 'api/video-links';

  constructor(protected http: HttpClient) {}

  create(videoLink: IVideoLink): Observable<EntityResponseType> {
    return this.http.post<IVideoLink>(this.resourceUrl, videoLink, { observe: 'response' });
  }

  update(videoLink: IVideoLink): Observable<EntityResponseType> {
    return this.http.put<IVideoLink>(this.resourceUrl, videoLink, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVideoLink>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVideoLink[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
