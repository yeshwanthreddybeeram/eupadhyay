import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMyVideos, MyVideos } from 'app/shared/model/my-videos.model';
import { MyVideosService } from './my-videos.service';
import { MyVideosComponent } from './my-videos.component';
import { MyVideosDetailComponent } from './my-videos-detail.component';
import { MyVideosUpdateComponent } from './my-videos-update.component';

@Injectable({ providedIn: 'root' })
export class MyVideosResolve implements Resolve<IMyVideos> {
  constructor(private service: MyVideosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMyVideos> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((myVideos: HttpResponse<MyVideos>) => {
          if (myVideos.body) {
            return of(myVideos.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MyVideos());
  }
}

export const myVideosRoute: Routes = [
  {
    path: '',
    component: MyVideosComponent,
    data: {
      authorities: [Authority.USER, Authority.EMPLOYEE],
      pageTitle: 'MyVideos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MyVideosDetailComponent,
    resolve: {
      myVideos: MyVideosResolve,
    },
    data: {
      authorities: [Authority.USER, Authority.EMPLOYEE],
      pageTitle: 'MyVideos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MyVideosUpdateComponent,
    resolve: {
      myVideos: MyVideosResolve,
    },
    data: {
      authorities: [Authority.USER, Authority.EMPLOYEE],
      pageTitle: 'MyVideos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MyVideosUpdateComponent,
    resolve: {
      myVideos: MyVideosResolve,
    },
    data: {
      authorities: [Authority.USER, Authority.EMPLOYEE],
      pageTitle: 'MyVideos',
    },
    canActivate: [UserRouteAccessService],
  },
];
