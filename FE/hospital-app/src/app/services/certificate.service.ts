import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {AuthService} from './auth.service';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private readonly ENDPOINT_REQUEST: string = "certificate/request";
  private readonly ENDPOINT_SEND_REQ: string = "certificate-request;"

  constructor(
    private http: HttpClient,
    private route: Router,
    private auth: AuthService
  ) { }

  requestCertificate(): Observable<any> {

    const headers = new HttpHeaders({
      'Authorization' : 'Bearer ' + this.auth.getToken()
    });

    return this.http.get(environment.HOSPITAL_APP + this.ENDPOINT_REQUEST, { headers: headers, responseType: 'arraybuffer' as 'text' })
  }

  sendRequestToAdmin(data: any) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.auth.getToken()
    });
    return this.http.post(environment.ADMIN_APP + this.ENDPOINT_SEND_REQ, data, {headers:headers});
  }

}
