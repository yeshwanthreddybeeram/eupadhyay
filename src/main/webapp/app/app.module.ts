import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DevpctSharedModule } from 'app/shared/shared.module';
import { DevpctCoreModule } from 'app/core/core.module';
import { DevpctAppRoutingModule } from './app-routing.module';
import { DevpctHomeModule } from './home/home.module';
import { DevpctEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DevpctSharedModule,
    DevpctCoreModule,
    DevpctHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DevpctEntityModule,
    DevpctAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class DevpctAppModule {}
