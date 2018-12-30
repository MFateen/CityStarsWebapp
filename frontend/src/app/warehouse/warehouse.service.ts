import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Response} from '../shared/models';
import {PurchaseRequest, Spare, SpareType, StockIssue, StockReceived} from './warehhouse.dto';

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


  getPurchaseRequests() {
	return this.http.get<Response<PurchaseRequest>>(`${environment.apiUrl}/vouchers/purchaserequest/getAll`);
  }

  addPurchaseRequest(purchaseRequest: PurchaseRequest) {
    return this.http.post(`${environment.apiUrl}/vouchers/purchaserequest/create`, purchaseRequest);
  }

  getStockReceived() {
    return this.http.get<Response<StockReceived>>(`${environment.apiUrl}/vouchers/stockreceived/getAll`);
  }

  addStockReceived(stockReceived: StockReceived) {
    return this.http.post(`${environment.apiUrl}/vouchers/stockreceived/create`, stockReceived);
  }

  getStockIssue() {
    return this.http.get<Response<StockIssue>>(`${environment.apiUrl}/vouchers/stockissue/getAll`);
  }

  addStockIssue(stockIssue: StockIssue) {
    return this.http.post(`${environment.apiUrl}/vouchers/stockissue/create`, stockIssue);
  }

}
