import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Response} from '../shared/models';
import {PurchaseRequest, Spare, SpareType, StockIssue} from './warehhouse.dto';

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {

  constructor(private http: HttpClient) { }

  // Spare Types api
  getSpareTypesList() {
    return this.http.get<Response<SpareType>>(`${environment.apiUrl}/sparetype/getAll`);
  }

  // Spare Parts api
  getSparesList() {
    return this.http.get<Response<Spare>>(`${environment.apiUrl}/spare/getAll`);
  }

  addPurchaseRequest(purchaseRequest: PurchaseRequest) {
    return this.http.post(`${environment.apiUrl}/vouchers/purchaserequest/create`, purchaseRequest);
  }

  addStockIssue(stockIssue: StockIssue) {
    return this.http.post(`${environment.apiUrl}/vouchers/stockissue/create`, stockIssue);
  }

}
