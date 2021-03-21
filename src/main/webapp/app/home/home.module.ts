import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DevpctSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

@NgModule({
  imports: [DevpctSharedModule, MDBBootstrapModule.forRoot(), RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class DevpctHomeModule {}
