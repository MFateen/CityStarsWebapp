import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {map, startWith} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {PurchaseRequest, SpareType, VoucherItem} from '../../warehhouse.dto';
import {WarehouseService} from '../../warehouse.service';
import {AlertService} from '../../../shared/services';

@Component({
  selector: 'app-purchase-request',
  templateUrl: './purchase-request.component.html',
  styleUrls: ['./purchase-request.component.scss'],
  providers: [WarehouseService, AlertService]
})
export class PurchaseRequestComponent implements OnInit, AfterViewInit {

  @ViewChild('newPurchaseModal') newPurchaseModal: ModalDirective;

  itemForm: FormGroup;
  purchaseForm: FormGroup;

  voucherItems: VoucherItem[] = [];
  selectedItem: VoucherItem;

  filteredCodes: Observable<SpareType[]>;
  spareTypes: SpareType[] = [];

  isViewInit = false;
  showItemForm = false;

  @Input() set chosenPurchaseRequest( pr: PurchaseRequest) {
    if (!this.purchaseForm) {
      return;
    }
    this.purchaseForm.controls['date'].setValue(pr.date);
    this.purchaseForm.controls['needsRequest'].setValue(pr.needsRequest);
    this.purchaseForm.controls['supplierCode'].setValue(pr.supplierCode);
    this.purchaseForm.controls['supplierName'].setValue(pr.supplierName);
    this.voucherItems = pr.voucherItemRequests;
  }
  @Input() isViewOnly: boolean;
  @Input()
  set showModal(show: boolean) {
    if (!this.isViewInit) {
      return;
    }
    this.showPurchaseModal();
  }

  constructor(
    private fb: FormBuilder,
    private warehouseService: WarehouseService,
    private alert: AlertService
  ) { }

  ngOnInit() {
    if (this.isViewOnly === undefined) {
      this.isViewOnly = false;
    }
    this.warehouseService.getSpareTypesList().subscribe(res => {
      this.spareTypes = res.content;
    });

    this.itemForm = this.fb.group({
      spareType: ['', Validators.required],
      refCode: ['', Validators.required],
      quantity: ['', Validators.required],
      make: ['', Validators.required],
      unit: ['', Validators.required],
      description: ['', Validators.required]
    });

    this.purchaseForm = this.fb.group({
      date: ['', Validators.required],
      needsRequest: ['', Validators.required],
      supplierCode: ['', Validators.required],
      supplierName: ['', Validators.required]
    });

    this.filteredCodes = this.itemForm.get('spareType').valueChanges
      .pipe(
        startWith(''),
        map(st => st ? this._filterSpareTypes(st) : this.spareTypes.slice())
      );

    if (this.isViewOnly) {
      this.purchaseForm.disable();
    }
  }

  private _filterSpareTypes(spareType): SpareType[] {
    return this.spareTypes.filter(st => st.name.indexOf(spareType) === 0);
  }

  ngAfterViewInit() {
    this.isViewInit = true;
  }

  showPurchaseModal(): void {
    this.newPurchaseModal.show();
  }

  hidePurchaseModal(): void {
    this.newPurchaseModal.hide();
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

  addPurchaseRequest() {
    const purchaseRequest: PurchaseRequest = this.purchaseForm.value;

    purchaseRequest.voucherItemRequests = this.voucherItems;
    this.warehouseService.addPurchaseRequest(purchaseRequest).subscribe(res => {
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
