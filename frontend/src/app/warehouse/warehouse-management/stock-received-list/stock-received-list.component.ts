import { Component, OnInit } from '@angular/core';
import {WarehouseService} from '../../warehouse.service';
import {StockReceived} from '../../warehhouse.dto';

@Component({
  selector: 'app-stock-received-list',
  templateUrl: './stock-received-list.component.html',
  styleUrls: ['./stock-received-list.component.scss']
})
export class StockReceivedListComponent implements OnInit {

  stockReceivedList: StockReceived[] = [];

  constructor(
    private warehouseService: WarehouseService
  ) { }

  ngOnInit() {
    this.warehouseService.getStockReceived().subscribe(res => {
      this.stockReceivedList = res.content;
    });
  }

}
