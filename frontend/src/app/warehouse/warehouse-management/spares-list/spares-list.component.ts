import {Component, OnInit} from '@angular/core';
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

  checkedList: Number[] = [];
  filteredSpares: Spare[] = [];

  constructor(
    private warehouseService: WarehouseService
  ) {
  }

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
        this.filteredSpares = this.spares;
        console.log(this.spares);
      }
    );
  }

  checkBoxClick(isChecked, id) {
    const index = this.checkedList.indexOf(id, 0);
    if (index > -1) {
      this.checkedList.splice(index, 1);
    } else {
      this.checkedList.push(id);
    }
    if (this.checkedList.length === 0) {
      this.filteredSpares = this.spares;
    } else {
      this.filteredSpares = this.spares.filter(spare => this.checkedList.includes(spare.spareTypeID));
    }
  }

}
