import {Component, OnInit} from '@angular/core';
import {WarehouseService} from '../warehouse.service';
import {Spare, SpareType} from '../warehhouse.dto';

@Component({
  selector: 'app-warehouse-management',
  templateUrl: './warehouse-management.component.html',
  styleUrls: ['./warehouse-management.component.scss']
})
export class WarehouseManagementComponent implements OnInit {
  showPurchase: boolean;
  showStockReceived: boolean;
  showStockIssue: boolean;

  constructor() {
  }

  ngOnInit() {
    this.showPurchase = false;
    this.showStockReceived = false;
    this.showStockIssue = false;
  }

  onPurchaseClick() {
    this.showPurchase = !this.showPurchase;
  }

  onStockReceivedClick() {
    this.showStockReceived = !this.showStockReceived;
  }

  onStockIssueClick() {
    this.showStockIssue = !this.showStockIssue;
  }
}
