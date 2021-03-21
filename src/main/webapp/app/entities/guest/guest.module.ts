import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { GuestComponent } from './guest.component';
import { GuestDetailComponent } from './guest-detail.component';
import { GuestUpdateComponent } from './guest-update.component';
import { GuestDeleteDialogComponent } from './guest-delete-dialog.component';
import { guestRoute } from './guest.route';

@NgModule({
  imports: [DevpctSharedModule, RouterModule.forChild(guestRoute)],
  declarations: [GuestComponent, GuestDetailComponent, GuestUpdateComponent, GuestDeleteDialogComponent],
  entryComponents: [GuestDeleteDialogComponent],
})
export class DevpctGuestModule {}
