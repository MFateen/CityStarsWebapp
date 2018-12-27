import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout/layout.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { AlertComponent } from './alert/alert.component';
import {AppRoutingModule} from '../app-routing.module';
import {NgProgressModule} from '@ngx-progressbar/core';
import {NgProgressHttpModule} from '@ngx-progressbar/http';
import {HttpClientModule} from '@angular/common/http';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';

// TODO: only include needed icons
library.add(fas, far);


@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    FontAwesomeModule,
    NgProgressModule.forRoot({
      direction: 'rtl+',
      spinnerPosition: 'left',
      color: '#FFA81F'
    }),
    NgProgressHttpModule.forRoot()
  ],
  declarations: [LayoutComponent, HeaderComponent, FooterComponent, AlertComponent],
  exports: [
    LayoutComponent,
    FontAwesomeModule,
    AlertComponent
  ]
})
export class UiModule { }
