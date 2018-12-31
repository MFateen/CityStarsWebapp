import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [

  {
    path: '', canActivate: [AuthGuard], children: [
      {path: '', pathMatch: 'full', redirectTo: 'admin'},
      {path: 'admin', component: HomeComponent},
      {path: 'buses', component: BusesListComponent},
      {
        path: 'buses/:id', component: BusDetailsComponent, children: [
          {path: '', redirectTo: 'events', pathMatch: 'full'},
          {path: 'events', component: BusEventsComponent},
          {path: 'spares', component: BusSparesComponent}
        ]
      },
      {path: 'vouchers', component: VouchersComponent},
      {path: 'user', component: ProfileComponent},
      {path: 'warehouse', component: WarehouseManagementComponent, children: [
          {path: '', redirectTo: 'spares', pathMatch: 'full'},
          {path: 'spares', component: SparesListComponent},
          {path: 'purchase-requests', component: PurchaseRequestListComponent},
          {path: 'stock-received', component: StockReceivedListComponent},
          {path: 'stock-issue', component: StockIssueListComponent}
        ]}
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: '404', component: PageNotFoundComponent},
  {path: '**', pathMatch: 'full', redirectTo: '404'}
];


import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {HomeComponent} from './home/home.component';
import {AuthGuard} from './shared/guards';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {BusDetailsComponent} from './bus/bus-details/bus-details.component';
import {VouchersComponent} from './vouchers/vouchers.component';
import {ProfileComponent} from './profile/profile.component';
import {BusEventsComponent} from './bus/bus-details/bus-events/bus-events.component';
import {BusSparesComponent} from './bus/bus-details/bus-spares/bus-spares.component';
import {BusesListComponent} from './bus/buses-list/buses-list.component';
import {WarehouseManagementComponent} from './warehouse/warehouse-management/warehouse-management.component';
import {SparesListComponent} from './warehouse/warehouse-management/spares-list/spares-list.component';
import {PurchaseRequestListComponent} from './warehouse/warehouse-management/purchase-request-list/purchase-request-list.component';
import {StockReceivedListComponent} from './warehouse/warehouse-management/stock-received-list/stock-received-list.component';
import {StockIssueListComponent} from './warehouse/warehouse-management/stock-issue-list/stock-issue-list.component';


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {
}
