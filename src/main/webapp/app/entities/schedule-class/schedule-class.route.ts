import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScheduleClass, ScheduleClass } from 'app/shared/model/schedule-class.model';
import { ScheduleClassService } from './schedule-class.service';
import { ScheduleClassComponent } from './schedule-class.component';
import { ScheduleClassDetailComponent } from './schedule-class-detail.component';
import { ScheduleClassUpdateComponent } from './schedule-class-update.component';

@Injectable({ providedIn: 'root' })
export class ScheduleClassResolve implements Resolve<IScheduleClass> {
  constructor(private service: ScheduleClassService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScheduleClass> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scheduleClass: HttpResponse<ScheduleClass>) => {
          if (scheduleClass.body) {
            return of(scheduleClass.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ScheduleClass());
  }
}

export const scheduleClassRoute: Routes = [
  {
    path: '',
    component: ScheduleClassComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ScheduleClasses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScheduleClassDetailComponent,
    resolve: {
      scheduleClass: ScheduleClassResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ScheduleClasses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScheduleClassUpdateComponent,
    resolve: {
      scheduleClass: ScheduleClassResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ScheduleClasses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScheduleClassUpdateComponent,
    resolve: {
      scheduleClass: ScheduleClassResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ScheduleClasses',
    },
    canActivate: [UserRouteAccessService],
  },
];
