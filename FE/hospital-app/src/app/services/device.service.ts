import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {PatientModel} from "../models/patient.model";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private readonly HEART_DEVICE_API: string = "heart-data/by-page/";

  constructor(
    private http: HttpClient
  ) { }

  getAll(page: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get<Array<any>>(environment.HOSPITAL_APP + this.HEART_DEVICE_API + page, {headers:headers});
  }

}
