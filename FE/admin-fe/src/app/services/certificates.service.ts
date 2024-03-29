import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CertificatesService {

  private readonly allCertificates: string = "certificate";
  private readonly allRequests: string = "certificate-request";
  private readonly denyRequest: string = "certificate-request/denyRequest";
  private readonly createCertificate: string = "certificate/generateCertificate";
  private readonly oneCertificate: string = "certificate/getOne";
  private readonly revoke: string = "certificate/revokeCertificate";

  constructor(
    private http: HttpClient,
    private route: Router,
    private authService: AuthService
  ) { }

  getAll(): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.get(environment.ADMIN_APP+ this.allCertificates, {headers:headers});
  }

  approveForCertificate(id: number, ext: string): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });

    const data = {
      id: id,
      template: ext
    };

    return this.http.post(environment.ADMIN_APP+ this.createCertificate, data, {headers:headers});
  }

  denyCertificateRequest(id: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.post(environment.ADMIN_APP+ this.denyRequest, id, {headers:headers});
  }

  getOne(serialNum: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    const pathParam = new HttpParams().set("serialNumber", serialNum.toString());

    return this.http.get(environment.ADMIN_APP+ this.oneCertificate,{headers:headers, params: pathParam});
  }

  getAllCertificateRequests(): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.get(environment.ADMIN_APP+ this.allRequests, {headers:headers});
  }

  revokeCertificate(data: any): Observable<any> {

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });

    const params = new HttpParams()
    .set('serialNumber', data.serialNumber)
    .set('revokeReason', data.revokeReason);

    return this.http.post(environment.ADMIN_APP+ this.revoke,null, {headers:headers,params:params});

  }
}
