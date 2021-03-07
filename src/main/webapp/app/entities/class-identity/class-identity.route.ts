import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClassIdentity, ClassIdentity } from 'app/shared/model/class-identity.model';
import { ClassIdentityService } from './class-identity.service';
import { ClassIdentityComponent } from './class-identity.component';
import { ClassIdentityDetailComponent } from './class-identity-detail.component';
import { ClassIdentityUpdateComponent } from './class-identity-update.component';

@Injectable({ providedIn: 'root' })
export class ClassIdentityResolve implements Resolve<IClassIdentity> {
  constructor(private service: ClassIdentityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassIdentity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((classIdentity: HttpResponse<ClassIdentity>) => {
          if (classIdentity.body) {
            return of(classIdentity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassIdentity());
  }
}

export const classIdentityRoute: Routes = [
  {
    path: '',
    component: ClassIdentityComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ClassIdentities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassIdentityDetailComponent,
    resolve: {
      classIdentity: ClassIdentityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ClassIdentities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassIdentityUpdateComponent,
    resolve: {
      classIdentity: ClassIdentityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ClassIdentities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassIdentityUpdateComponent,
    resolve: {
      classIdentity: ClassIdentityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ClassIdentities',
    },
    canActivate: [UserRouteAccessService],
  },
];