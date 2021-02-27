import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { MyVideosComponent } from './my-videos.component';
import { MyVideosDetailComponent } from './my-videos-detail.component';
import { MyVideosUpdateComponent } from './my-videos-update.component';
import { MyVideosDeleteDialogComponent } from './my-videos-delete-dialog.component';
import { myVideosRoute } from './my-videos.route';

@NgModule({
  imports: [DevpctSharedModule, RouterModule.forChild(myVideosRoute)],
  declarations: [MyVideosComponent, MyVideosDetailComponent, MyVideosUpdateComponent, MyVideosDeleteDialogComponent],
  entryComponents: [MyVideosDeleteDialogComponent],
})
export class DevpctMyVideosModule {}
