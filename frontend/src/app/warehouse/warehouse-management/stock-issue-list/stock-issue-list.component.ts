import { Component, OnInit } from '@angular/core';
import {StockIssue} from '../../warehhouse.dto';
import {WarehouseService} from '../../warehouse.service';

@Component({
  selector: 'app-stock-issue-list',
  templateUrl: './stock-issue-list.component.html',
  styleUrls: ['./stock-issue-list.component.scss']
})
export class StockIssueListComponent implements OnInit {

  stockIssueList: StockIssue[] = [];

  constructor(
    private warehouseService: WarehouseService
  ) { }

  ngOnInit() {
    this.warehouseService.getStockIssue().subscribe(res => {
      this.stockIssueList = res.content;
    });
  }

}
