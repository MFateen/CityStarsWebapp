import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SpareType, VoucherItem} from '../../warehhouse.dto';
import {Bus} from '../../../bus/bus.dto';
import {BusService} from '../../../bus/bus.service';
import {WarehouseService} from '../../warehouse.service';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';

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

  filteredCodes: Observable<SpareType[]>;
  spareTypes: SpareType[] = [];

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
    private busService: BusService,
    private warehouseService: WarehouseService
  ) { }

  ngOnInit() {
    this.warehouseService.getSpareTypesList().subscribe(res => {
      this.spareTypes = res.content;
    });

    this.stockForm = this.fb.group({
      date: ['', Validators.required],
      bus: ['', Validators.required],
    });

    this.itemForm = this.fb.group({
      spareType: ['', Validators.required],
      description: ['', Validators.required],
      unit: ['', Validators.required],
      quantity: ['', Validators.required],
      notes: ['', Validators.required]
    });

    this.retrieveData();

    this.filteredCodes = this.itemForm.get('spareType').valueChanges
      .pipe(
        startWith(''),
        map(st => st ? this._filterSpareTypes(st) : this.spareTypes.slice())
      );
  }

  private _filterSpareTypes(spareType): SpareType[] {
    return this.spareTypes.filter(st => st.name.indexOf(spareType) === 0);
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

  displayFn(spareType?: SpareType): string | undefined {
    return spareType ? spareType.name : undefined;
  }
}
