import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localeArEg from '@angular/common/locales/ar-EG';
import { AppComponent } from './app.component';
import { UiModule } from './ui/ui.module';
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {AuthGuard} from './shared/guards';
import {AlertService, AuthenticationService, UserService} from './shared/services';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ErrorInterceptor, JwtInterceptor} from './shared/helpers';
import {ReactiveFormsModule} from '@angular/forms';
import { BusDetailsComponent } from './bus/bus-details/bus-details.component';
import {NgxGaugeModule} from 'ngx-gauge';
import { VouchersComponent } from './vouchers/vouchers.component';
import { ProfileComponent } from './profile/profile.component';
import { BusEventsComponent } from './bus/bus-details/bus-events/bus-events.component';
import {Ng2OdometerModule} from 'ng2-odometer';
import { BusesListComponent } from './bus/buses-list/buses-list.component';
import {ModalModule} from 'ngx-bootstrap';
import { WarehouseManagementComponent } from './warehouse/warehouse-management/warehouse-management.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AppMaterialModule} from './app-material/app-material.module';
import { PurchaseRequestComponent } from './warehouse/warehouse-management/purchase-request/purchase-request.component';
import { StockReceivedComponent } from './warehouse/warehouse-management/stock-received/stock-received.component';
import { BusSparesComponent } from './bus/bus-details/bus-spares/bus-spares.component';
import { StockIssueComponent } from './warehouse/warehouse-management/stock-issue/stock-issue.component';
import { NewBusEventComponent } from './bus/bus-details/new-bus-event/new-bus-event.component';
import { PurchaseRequestListComponent } from './warehouse/warehouse-management/purchase-request-list/purchase-request-list.component';
import { StockIssueListComponent } from './warehouse/warehouse-management/stock-issue-list/stock-issue-list.component';
import { StockReceivedListComponent } from './warehouse/warehouse-management/stock-received-list/stock-received-list.component';
import { SparesListComponent } from './warehouse/warehouse-management/spares-list/spares-list.component';

registerLocaleData(localeArEg);

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    BusDetailsComponent,
    VouchersComponent,
    ProfileComponent,
    BusEventsComponent,
    BusesListComponent,
    WarehouseManagementComponent,
    PurchaseRequestComponent,
    BusSparesComponent,
    StockReceivedComponent,
    StockIssueComponent,
    NewBusEventComponent,
    PurchaseRequestListComponent,
    StockIssueListComponent,
    StockReceivedListComponent,
    SparesListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    UiModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgxGaugeModule,
    Ng2OdometerModule.forRoot(),
    ModalModule.forRoot(),
    BrowserAnimationsModule,
    AppMaterialModule
  ],
  providers: [
    AuthGuard,
    AlertService,
    AuthenticationService,
    UserService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: LOCALE_ID, useValue: 'ar-eg' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
