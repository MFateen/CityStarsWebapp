import {Component, OnInit} from '@angular/core';
import {WarehouseService} from '../warehouse.service';
import {Spare, SpareType} from '../warehhouse.dto';

@Component({
  selector: 'app-warehouse-management',
  templateUrl: './warehouse-management.component.html',
  styleUrls: ['./warehouse-management.component.scss']
})
export class WarehouseManagementComponent implements OnInit {


  spareTypes: SpareType[] = [];
  spares: Spare[] = [];
  showPurchase: boolean;
  showStockReceived: boolean;
  showStockIssue: boolean;

  constructor(
    private warehouseService: WarehouseService
  ) {  }

  ngOnInit() {
    this.showPurchase = false;
    this.showStockReceived = false;
    this.showStockIssue = false;
    this.warehouseService.getSpareTypesList().subscribe(
      res => {
        this.spareTypes = res.content;
      }
    );

    this.warehouseService.getSparesList().subscribe(
      res => {
        this.spares = res.content;
      }
    );
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
