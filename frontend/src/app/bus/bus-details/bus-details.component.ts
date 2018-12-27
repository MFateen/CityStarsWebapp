import {Component, OnInit} from '@angular/core';
import {BusService} from '../bus.service';
import {Bus} from '../bus.dto';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-bus-details',
  templateUrl: './bus-details.component.html',
  styleUrls: ['./bus-details.component.scss'],
  providers: [BusService]
})
export class BusDetailsComponent implements OnInit {

  busID: number;
  bus: Bus;

  showNewBusEvent: boolean;

  constructor(
    private busService: BusService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.showNewBusEvent = false;
    this.busID = this.route.snapshot.params['id'];

    this.busService.getBus(this.busID).subscribe(
      bus => {
        this.bus = bus;
      }
    );
  }

  onNewBusEventClick() {
    this.showNewBusEvent = !this.showNewBusEvent;
  }

}
