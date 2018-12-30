import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {map, startWith} from 'rxjs/operators'
import {Observable} from 'rxjs';
import {PurchaseRequest, StockReceived, SpareType, VoucherItem} from '../../warehhouse.dto';
import {WarehouseService} from '../../warehouse.service';
import {AlertService} from '../../../shared/services';

@Component({
  selector: 'app-stock-received',
  templateUrl: './stock-received.component.html',
  styleUrls: ['./stock-received.component.scss'],
  providers: [WarehouseService, AlertService]
})
export class StockReceivedComponent implements OnInit, AfterViewInit {

  @ViewChild('newStockReceivedModal') newStockReceivedModal: ModalDirective;

  stockForm: FormGroup;
  itemForm: FormGroup;

  voucherItems: VoucherItem[] = [];
  selectedItem: VoucherItem;

  purchaseRequests: PurchaseRequest[] = [];

  filteredCodes: Observable<SpareType[]>;
  spareTypes: SpareType[] = [];

  isViewInit = false;
  showItemForm = false;

  @Input()
  set showModal(show: boolean) {
    if (!this.isViewInit) { return; }
    this.showStockReceivedModal();
  }

  constructor(
    private fb: FormBuilder,
    private warehouseService: WarehouseService,
    private alert: AlertService
  ) { }

  ngOnInit() {
    this.warehouseService.getSpareTypesList().subscribe(res => {
      this.spareTypes = res.content;
    });

    this.warehouseService.getPurchaseRequests().subscribe(res => {
      this.purchaseRequests = res.content;
    });

    this.itemForm = this.fb.group({
      spareType: ['', Validators.required],
      refCode: ['', Validators.required],
      quantity: ['', Validators.required],
      make: ['', Validators.required],
      unit: ['', Validators.required],
      description: ['', Validators.required]
    });

    this.stockForm = this.fb.group({
      date: ['', Validators.required],
      purchaseRequests: ['', Validators.required],
    });

	this.filteredCodes = this.itemForm.get('spareType').valueChanges
      .pipe(
        startWith(''),
        map(st => st ? this._filterSpareTypes(st) : this.spareTypes.slice())
      );
  }

  private _filterSpareTypes(spareType): SpareType[] {
    return this.spareTypes.filter(st => st.name.indexOf(spareType) === 0);
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
    const voucherItem: VoucherItem = this.itemForm.value;
    if (this.itemForm.controls['spareType'].value.name) {
      voucherItem.spareType = this.itemForm.controls['spareType'].value;
    } else {
      voucherItem.spareType = new SpareType();
      voucherItem.spareType.name = this.itemForm.controls['spareType'].value;
      voucherItem.spareType.id = -1;
    }
    this.voucherItems.push(voucherItem);
    this.itemForm.reset();
  }

  addStockReceived() {
	const stockReceived: StockReceived = this.stockForm.value;

    stockReceived.voucherItemRequests = this.voucherItems;
	stockReceived.purchaseOrder = this.stockForm.controls['purchaseRequests'].value;
    this.warehouseService.addStockReceived(stockReceived).subscribe(res => {
      this.alert.success('تمت الإضافة بنجاح');
    });
  }

  selectItem(item) {
    this.selectedItem = this.selectedItem === item ? null : item;
  }

  displayFn(spareType?: SpareType): string | undefined {
    return spareType ? spareType.name : undefined;
  }
}
