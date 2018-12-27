import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Bus, BusEvent} from './bus.dto';
import {Response} from '../shared/models';
import {Spare} from '../warehouse/warehhouse.dto';

@Injectable({
  providedIn: 'root'
})
export class BusService {

  constructor(private http: HttpClient) { }

  getBusesList() {
    return this.http.get<Response<Bus>>(`${environment.apiUrl}/bus/getAll`);
  }

  getBus(id: number) {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Bus>(`${environment.apiUrl}/bus/get`, { headers: new HttpHeaders(), params: params });
  }

  getBusEvents(id: number) {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<BusEvent[]>(`${environment.apiUrl}/bus/event/getEventsForBus`, { headers: new HttpHeaders(), params: params });
  }

  addBusEvents(busEvent: BusEvent) {
    return this.http.post(`${environment.apiUrl}/bus/event/add`, busEvent);
  }

  getBusSpares(id: number) {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Spare[]>(`${environment.apiUrl}/bus/spare/getSparesForBus`, { headers: new HttpHeaders(), params: params });
  }

  createBus(bus: Bus) {
    return this.http.post(`${environment.apiUrl}/bus/create`, bus);
  }

  editBus(bus: Bus) {
    return this.http.post(`${environment.apiUrl}/bus/edit`, bus);
  }

  deleteBus(busID: number) {
    const params = new HttpParams().set('id', busID.toString());
    return this.http.delete(`${environment.apiUrl}/bus/delete`, {headers: new HttpHeaders(), params: params});
  }
}
