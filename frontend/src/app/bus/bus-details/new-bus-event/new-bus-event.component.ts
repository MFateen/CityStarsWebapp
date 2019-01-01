import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BusService} from '../../bus.service';
import {ModalDirective} from 'ngx-bootstrap';
import {Bus, BusEvent, BusEventType} from '../../bus.dto';

@Component({
  selector: 'app-new-bus-event',
  templateUrl: './new-bus-event.component.html',
  styleUrls: ['./new-bus-event.component.scss']
})
export class NewBusEventComponent implements OnInit, AfterViewInit {

  @ViewChild('newBusEvent') newBusEvent: ModalDirective;

  busEvent: FormGroup;

  isViewInit = false;
  @Input() bus: Bus;

  @Input()
  set showModal(show: boolean) {
    if (!this.isViewInit) {
      return;
    }
    this.showBusEventModal();
  }

  busEventTypesList: Object;

  constructor(
    private fb: FormBuilder,
    private busService: BusService
  ) {
  }

  ngOnInit() {
    this.busEvent = this.fb.group({
      text: ['', Validators.required],
      busCondition: ['', Validators.required],
      busEventType: ['', Validators.required],
    });

    this.retrieveData();
    this.busEventTypes();
  }

  busEventTypes() {

    this.busEventTypesList = Object.keys(BusEventType).filter(
      (BusEventType) => isNaN(<any>BusEventType) && BusEventType !== 'values');
    return Object.keys(BusEventType).filter(
      (BusEventType) => isNaN(<any>BusEventType) && BusEventType !== 'values'
    );
  }

  getBusEventInArabic(busEventName : string) {

    switch (busEventName) {
      case 'SIGNAL_ISSUE':
        return 'عطل جديد';
        break;
      case 'RESOLVE_ISSUE':
        return 'حل مشكلة سابقة';
        break;
      case 'GENERAL':
        return 'ملاحظات أخرى';
        break;
      case 'TRIP_SCHEDULED':
        return 'رحلة جديدة';
        break;
      case 'SPARE_PART_ADDED':
        return 'قطعة غيار جديدة';
        break;
      default:
        return '';
        break;
    }
  }

  retrieveData() {

  }

  ngAfterViewInit() {
    this.isViewInit = true;
  }

  showBusEventModal(): void {
    this.newBusEvent.show();
  }

  hideBusEventModal(): void {
    this.newBusEvent.hide();
  }

  addBusEvent() {
    const busEvent: BusEvent = this.busEvent.value;
    busEvent.busId = this.bus.id;

    console.log(busEvent);
    this.busService.addBusEvents(busEvent).subscribe(res => {
      console.log(res);
    });
  }
}
