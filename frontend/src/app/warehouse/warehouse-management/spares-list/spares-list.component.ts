import { Component, OnInit } from '@angular/core';
import {WarehouseService} from '../../warehouse.service';
import {Spare, SpareType} from '../../warehhouse.dto';

@Component({
  selector: 'app-spares-list',
  templateUrl: './spares-list.component.html',
  styleUrls: ['./spares-list.component.scss']
})
export class SparesListComponent implements OnInit {

  spareTypes: SpareType[] = [];
  spares: Spare[] = [];

  constructor(
    private warehouseService: WarehouseService
  ) { }

  ngOnInit() {
    this.warehouseService.getSpareTypesList().subscribe(
      res => {
        this.spareTypes = res.content;
        console.log(this.spareTypes);
      }
    );

    this.warehouseService.getSparesList().subscribe(
      res => {
        this.spares = res.content;
        console.log(this.spares);
      }
    );
  }

}
