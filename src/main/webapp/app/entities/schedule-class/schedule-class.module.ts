import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { ScheduleClassComponent } from './schedule-class.component';
import { ScheduleClassDetailComponent } from './schedule-class-detail.component';
import { ScheduleClassUpdateComponent } from './schedule-class-update.component';
import { ScheduleClassDeleteDialogComponent } from './schedule-class-delete-dialog.component';
import { scheduleClassRoute } from './schedule-class.route';

@NgModule({
  imports: [DevpctSharedModule, RouterModule.forChild(scheduleClassRoute)],
  declarations: [ScheduleClassComponent, ScheduleClassDetailComponent, ScheduleClassUpdateComponent, ScheduleClassDeleteDialogComponent],
  entryComponents: [ScheduleClassDeleteDialogComponent],
})
export class DevpctScheduleClassModule {}
