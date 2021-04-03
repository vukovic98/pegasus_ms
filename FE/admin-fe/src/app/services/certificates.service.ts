import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CertificatesService {

  private readonly allCertificates: string = "certificate"
  private readonly oneCertificate: string = "certificate/getOne"

  constructor(private http: HttpClient, private route: Router) { }

  getAll(): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get(environment.ADMIN_APP+ this.allCertificates, {headers:headers});
  }

  getOne(serialNum: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    const pathParam = new HttpParams().set("serialNumber", serialNum.toString());

    return this.http.get(environment.ADMIN_APP+ this.oneCertificate,{headers:headers, params: pathParam});
  }
}
