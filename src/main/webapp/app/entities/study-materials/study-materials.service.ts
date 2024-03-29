import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStudyMaterials } from 'app/shared/model/study-materials.model';

type EntityResponseType = HttpResponse<IStudyMaterials>;
type EntityArrayResponseType = HttpResponse<IStudyMaterials[]>;

@Injectable({ providedIn: 'root' })
export class StudyMaterialsService {
  public resourceUrl = SERVER_API_URL + 'api/study-materials';

  constructor(protected http: HttpClient) {}

  create(studyMaterials: IStudyMaterials): Observable<EntityResponseType> {
    return this.http.post<IStudyMaterials>(this.resourceUrl, studyMaterials, { observe: 'response' });
  }

  update(studyMaterials: IStudyMaterials): Observable<EntityResponseType> {
    return this.http.put<IStudyMaterials>(this.resourceUrl, studyMaterials, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStudyMaterials>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStudyMaterials[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
