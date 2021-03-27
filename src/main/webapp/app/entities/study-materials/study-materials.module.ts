import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { StudyMaterialsComponent } from './study-materials.component';
import { StudyMaterialsDetailComponent } from './study-materials-detail.component';
import { StudyMaterialsUpdateComponent } from './study-materials-update.component';
import { StudyMaterialsDeleteDialogComponent } from './study-materials-delete-dialog.component';
import { studyMaterialsRoute } from './study-materials.route';

@NgModule({
  imports: [DevpctSharedModule, RouterModule.forChild(studyMaterialsRoute)],
  declarations: [
    StudyMaterialsComponent,
    StudyMaterialsDetailComponent,
    StudyMaterialsUpdateComponent,
    StudyMaterialsDeleteDialogComponent,
  ],
  entryComponents: [StudyMaterialsDeleteDialogComponent],
})
export class DevpctStudyMaterialsModule {}
