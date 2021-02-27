import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVideoLink, VideoLink } from 'app/shared/model/video-link.model';
import { VideoLinkService } from './video-link.service';
import { VideoLinkComponent } from './video-link.component';
import { VideoLinkDetailComponent } from './video-link-detail.component';
import { VideoLinkUpdateComponent } from './video-link-update.component';

@Injectable({ providedIn: 'root' })
export class VideoLinkResolve implements Resolve<IVideoLink> {
  constructor(private service: VideoLinkService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVideoLink> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((videoLink: HttpResponse<VideoLink>) => {
          if (videoLink.body) {
            return of(videoLink.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VideoLink());
  }
}

export const videoLinkRoute: Routes = [
  {
    path: '',
    component: VideoLinkComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VideoLinks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VideoLinkDetailComponent,
    resolve: {
      videoLink: VideoLinkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VideoLinks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VideoLinkUpdateComponent,
    resolve: {
      videoLink: VideoLinkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VideoLinks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VideoLinkUpdateComponent,
    resolve: {
      videoLink: VideoLinkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VideoLinks',
    },
    canActivate: [UserRouteAccessService],
  },
];
