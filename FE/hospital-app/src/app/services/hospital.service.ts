import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {LoginModel, loginResponse} from '../models/auth.model';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  private readonly ENDPOINT_CERTIFIED: string = "hospital/certified"
  private readonly ENDPOINT_REQUESTED: string = "hospital/requestedCertificate"


  constructor(
    private http: HttpClient,
    private route: Router,
    private auth: AuthService
  ) { }

  test(): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.auth.getToken()
    });

    return this.http.get(environment.HOSPITAL_APP + "auth/test", { headers: headers });
  }

  isVerified(id: number): Observable<any> {

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.auth.getToken()
    });

    return this.http.post<loginResponse>(environment.HOSPITAL_APP + this.ENDPOINT_CERTIFIED, id, { headers: headers });
  }

  hasRequestedCertificate(id: number): Observable<any> {

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.auth.getToken()
    });

    return this.http.post<loginResponse>(environment.HOSPITAL_APP + this.ENDPOINT_REQUESTED, id, { headers: headers });
  }
}
