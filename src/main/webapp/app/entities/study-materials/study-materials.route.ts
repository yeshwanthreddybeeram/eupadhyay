import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStudyMaterials, StudyMaterials } from 'app/shared/model/study-materials.model';
import { StudyMaterialsService } from './study-materials.service';
import { StudyMaterialsComponent } from './study-materials.component';
import { StudyMaterialsDetailComponent } from './study-materials-detail.component';
import { StudyMaterialsUpdateComponent } from './study-materials-update.component';

@Injectable({ providedIn: 'root' })
export class StudyMaterialsResolve implements Resolve<IStudyMaterials> {
  constructor(private service: StudyMaterialsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStudyMaterials> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((studyMaterials: HttpResponse<StudyMaterials>) => {
          if (studyMaterials.body) {
            return of(studyMaterials.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StudyMaterials());
  }
}

export const studyMaterialsRoute: Routes = [
  {
    path: '',
    component: StudyMaterialsComponent,
    data: {
      // authorities: [Authority.USER],
      pageTitle: 'StudyMaterials',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StudyMaterialsDetailComponent,
    resolve: {
      studyMaterials: StudyMaterialsResolve,
    },
    data: {
      // authorities: [Authority.USER],
      pageTitle: 'StudyMaterials',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StudyMaterialsUpdateComponent,
    resolve: {
      studyMaterials: StudyMaterialsResolve,
    },
    data: {
      // authorities: [Authority.USER],
      pageTitle: 'StudyMaterials',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StudyMaterialsUpdateComponent,
    resolve: {
      studyMaterials: StudyMaterialsResolve,
    },
    data: {
      // authorities: [Authority.USER],
      pageTitle: 'StudyMaterials',
    },
    canActivate: [UserRouteAccessService],
  },
];
