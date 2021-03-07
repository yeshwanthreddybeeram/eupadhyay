import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClassIdentity } from 'app/shared/model/class-identity.model';

type EntityResponseType = HttpResponse<IClassIdentity>;
type EntityArrayResponseType = HttpResponse<IClassIdentity[]>;

@Injectable({ providedIn: 'root' })
export class ClassIdentityService {
  public resourceUrl = SERVER_API_URL + 'api/class-identities';

  constructor(protected http: HttpClient) {}

  create(classIdentity: IClassIdentity): Observable<EntityResponseType> {
    return this.http.post<IClassIdentity>(this.resourceUrl, classIdentity, { observe: 'response' });
  }

  update(classIdentity: IClassIdentity): Observable<EntityResponseType> {
    return this.http.put<IClassIdentity>(this.resourceUrl, classIdentity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClassIdentity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassIdentity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
