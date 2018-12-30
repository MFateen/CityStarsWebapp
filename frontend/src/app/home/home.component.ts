import { Component, OnInit } from '@angular/core';
import {User} from '../shared/models';
import {UserService} from '../shared/services';
import {first} from 'rxjs/operators';
import {ModalDirective} from 'ngx-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {map, startWith} from 'rxjs/operators'
import {Observable} from 'rxjs';
import {PurchaseRequest, StockReceived, SpareType, VoucherItem, StockIssue} from '../warehouse/warehhouse.dto';
import {WarehouseService} from '../warehouse/warehouse.service';
import {AlertService} from '../shared/services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [WarehouseService, AlertService]
})
export class HomeComponent implements OnInit {
  currentUser: User;
  users: User[] = [];

  purchaseRequests: PurchaseRequest[] = [];
  stockReceivedList: StockReceived[] = [];
  stockIssueList: StockIssue[] = [];

  constructor(
    private warehouseService: WarehouseService,
    private alert: AlertService
  ) {}


  ngOnInit() {
    this.loadAllUsers();
    this.warehouseService.getPurchaseRequests().subscribe(res => {
      this.purchaseRequests = res.content;
    });
    this.warehouseService.getStockReceived().subscribe(res => {
      this.stockReceivedList= res.content;
    });
    this.warehouseService.getStockIssue().subscribe(res => {
      this.stockIssueList= res.content;
    })
  }

  private loadAllUsers() {
    // this.userService.getAll().pipe(first()).subscribe(users => {
    //   this.users = users;
    // });
  }

}
