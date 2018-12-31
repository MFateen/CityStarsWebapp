import { Component, OnInit } from '@angular/core';
import {PurchaseRequest} from '../../warehhouse.dto';
import {WarehouseService} from '../../warehouse.service';

@Component({
  selector: 'app-purchase-request-list',
  templateUrl: './purchase-request-list.component.html',
  styleUrls: ['./purchase-request-list.component.scss']
})
export class PurchaseRequestListComponent implements OnInit {

  purchaseRequests: PurchaseRequest[] = [];

  showPurchaseDetails: boolean;
  chosenPurchaseRequest: PurchaseRequest;

  constructor(
    private warehouseService: WarehouseService
  ) { }

  ngOnInit() {
    this.warehouseService.getPurchaseRequests().subscribe(res => {
      this.purchaseRequests = res.content;
    });
  }

  onDetailsClick(purchaseRequest) {
    this.showPurchaseDetails = !this.showPurchaseDetails;
    this.chosenPurchaseRequest = purchaseRequest;
  }

}
