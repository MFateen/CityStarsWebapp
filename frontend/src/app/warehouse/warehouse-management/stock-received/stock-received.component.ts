import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Bus} from '../../../bus/bus.dto';
import {VoucherItem} from '../../warehhouse.dto';
import {BusService} from '../../../bus/bus.service';

@Component({
  selector: 'app-stock-received',
  templateUrl: './stock-received.component.html',
  styleUrls: ['./stock-received.component.scss'],
  providers: [BusService]
})
export class StockReceivedComponent implements OnInit, AfterViewInit {

  @ViewChild('newStockReceivedModal') newStockReceivedModal: ModalDirective;

  stockForm: FormGroup;
  itemForm: FormGroup;

  stockItems: VoucherItem[] = [];
  selectedItem: VoucherItem;

  buses: Bus[];

  showItemForm = false;
  isViewInit = false;
  @Input()
  set showModal(show: boolean) {
    if (!this.isViewInit) { return; }
    this.showStockReceivedModal();
  }

  constructor(
    private fb: FormBuilder,
    private busService: BusService
  ) { }

  ngOnInit() {
    this.stockForm = this.fb.group({
      date: ['', Validators.required],
      bus: ['', Validators.required],
    });

    this.itemForm = this.fb.group({
      itemCode: ['', Validators.required],
      description: ['', Validators.required],
      unit: ['', Validators.required],
      quantity: ['', Validators.required],
      notes: ['', Validators.required]
    });

    this.retrieveData();
  }

  retrieveData() {
    this.busService.getBusesList().subscribe( res => {
      this.buses = res.content;
    });
  }

  ngAfterViewInit() {
    this.isViewInit = true;
  }

  showStockReceivedModal(): void {
    this.newStockReceivedModal.show();
  }

  hideStockReceivedModal(): void {
    this.newStockReceivedModal.hide();
  }

  addItem() {
    this.stockItems.push(this.itemForm.value);
    this.itemForm.reset();
  }

  addPurchaseRequest() {
  }

  selectItem(item) {
    this.selectedItem = this.selectedItem === item ? null : item;
  }

}
