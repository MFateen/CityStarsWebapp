import { Component, OnInit } from '@angular/core';
import {Spare} from '../../../warehouse/warehhouse.dto';
import {BusService} from '../../bus.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-bus-spares',
  templateUrl: './bus-spares.component.html',
  styleUrls: ['./bus-spares.component.scss'],
  providers: [BusService]
})
export class BusSparesComponent implements OnInit {

  busID: number;
  spares: Spare[] = [];

  constructor(
    private busService: BusService,
    route: ActivatedRoute
  ) {
    this.busID = route.parent.snapshot.params['id'];
  }

  ngOnInit() {
    this.busService.getBusSpares(this.busID).subscribe(
      spares => {
        this.spares = spares;
      }
    );
  }

}
