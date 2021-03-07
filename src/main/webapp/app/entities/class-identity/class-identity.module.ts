import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { ClassIdentityComponent } from './class-identity.component';
import { ClassIdentityDetailComponent } from './class-identity-detail.component';
import { ClassIdentityUpdateComponent } from './class-identity-update.component';
import { ClassIdentityDeleteDialogComponent } from './class-identity-delete-dialog.component';
import { classIdentityRoute } from './class-identity.route';

@NgModule({
  imports: [DevpctSharedModule, RouterModule.forChild(classIdentityRoute)],
  declarations: [ClassIdentityComponent, ClassIdentityDetailComponent, ClassIdentityUpdateComponent, ClassIdentityDeleteDialogComponent],
  entryComponents: [ClassIdentityDeleteDialogComponent],
})
export class DevpctClassIdentityModule {}
