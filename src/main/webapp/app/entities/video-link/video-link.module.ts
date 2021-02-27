import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { VideoLinkComponent } from './video-link.component';
import { VideoLinkDetailComponent } from './video-link-detail.component';
import { VideoLinkUpdateComponent } from './video-link-update.component';
import { VideoLinkDeleteDialogComponent } from './video-link-delete-dialog.component';
import { videoLinkRoute } from './video-link.route';

@NgModule({
  imports: [DevpctSharedModule, RouterModule.forChild(videoLinkRoute)],
  declarations: [VideoLinkComponent, VideoLinkDetailComponent, VideoLinkUpdateComponent, VideoLinkDeleteDialogComponent],
  entryComponents: [VideoLinkDeleteDialogComponent],
})
export class DevpctVideoLinkModule {}
