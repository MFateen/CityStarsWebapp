import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import {AlertService, AuthenticationService} from '../services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      const error = err.error.message || err.statusText;

      if (err.status === 401 && localStorage.getItem('currentUser')) {
        // auto logout if 401 response returned from api
        this.authenticationService.logout();
        location.reload(true);
      } else if (err.status === 404) {
        location.replace('404');
      } else {
        this.alertService.error('برجاء مراجعة البيانات واعادة المحاولة');
      }
      return throwError(error);
    }));
  }
}
