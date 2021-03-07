import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { AssignmentQAComponent } from './assignment-qa.component';
import { AssignmentQADetailComponent } from './assignment-qa-detail.component';
import { AssignmentQAUpdateComponent } from './assignment-qa-update.component';
import { AssignmentQADeleteDialogComponent } from './assignment-qa-delete-dialog.component';
import { assignmentQARoute } from './assignment-qa.route';

@NgModule({
  imports: [DevpctSharedModule, RouterModule.forChild(assignmentQARoute)],
  declarations: [AssignmentQAComponent, AssignmentQADetailComponent, AssignmentQAUpdateComponent, AssignmentQADeleteDialogComponent],
  entryComponents: [AssignmentQADeleteDialogComponent],
})
export class DevpctAssignmentQAModule {}
