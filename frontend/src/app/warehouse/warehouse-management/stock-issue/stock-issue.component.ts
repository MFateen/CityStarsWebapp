import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {VoucherItem} from '../../warehhouse.dto';
import {Bus} from '../../../bus/bus.dto';
import {BusService} from '../../../bus/bus.service';

@Component({
  selector: 'app-stock-issue',
  templateUrl: './stock-issue.component.html',
  styleUrls: ['./stock-issue.component.scss'],
  providers: [BusService]
})
export class StockIssueComponent implements OnInit, AfterViewInit {

  @ViewChild('newStockIssueModal') newStockIssueModal: ModalDirective;

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
    this.showStockIssueModal();
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

  showStockIssueModal(): void {
    this.newStockIssueModal.show();
  }

  hideStockIssueModal(): void {
    this.newStockIssueModal.hide();
  }

  addItem() {
    this.stockItems.push(this.itemForm.value);
    this.itemForm.reset();
  }

  addStockIssue() {
  }

  selectItem(item) {
    this.selectedItem = this.selectedItem === item ? null : item;
  }
}
