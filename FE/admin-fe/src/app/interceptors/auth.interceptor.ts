import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {catchError} from 'rxjs/operators';
import Swal from 'sweetalert2';
import {AuthService} from '../services/auth.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(
    private router: Router,
    private authService: AuthService
    ) {
  }

  private handleAuthError(err: HttpErrorResponse): Observable<any> {
    if (err.status === 401 || err.status === 403) {

      this.authService.logout();

      this.router.navigate(['/login']);

      Swal.fire({
        icon: 'error',
        title: 'Your session has expired. Please login again.',
        showConfirmButton: false,
        timer: 2000
      })

      return of(err.message);
    }
    return throwError(err);
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(x=> this.handleAuthError(x)));
  }

}
