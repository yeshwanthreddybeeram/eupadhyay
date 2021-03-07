import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAssignmentQA, AssignmentQA } from 'app/shared/model/assignment-qa.model';
import { AssignmentQAService } from './assignment-qa.service';
import { AssignmentQAComponent } from './assignment-qa.component';
import { AssignmentQADetailComponent } from './assignment-qa-detail.component';
import { AssignmentQAUpdateComponent } from './assignment-qa-update.component';

@Injectable({ providedIn: 'root' })
export class AssignmentQAResolve implements Resolve<IAssignmentQA> {
  constructor(private service: AssignmentQAService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssignmentQA> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((assignmentQA: HttpResponse<AssignmentQA>) => {
          if (assignmentQA.body) {
            return of(assignmentQA.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AssignmentQA());
  }
}

export const assignmentQARoute: Routes = [
  {
    path: '',
    component: AssignmentQAComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AssignmentQAS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssignmentQADetailComponent,
    resolve: {
      assignmentQA: AssignmentQAResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AssignmentQAS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssignmentQAUpdateComponent,
    resolve: {
      assignmentQA: AssignmentQAResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AssignmentQAS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssignmentQAUpdateComponent,
    resolve: {
      assignmentQA: AssignmentQAResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AssignmentQAS',
    },
    canActivate: [UserRouteAccessService],
  },
];
