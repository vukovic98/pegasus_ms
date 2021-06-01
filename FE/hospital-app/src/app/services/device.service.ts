import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AlarmModel} from '../models/alarm.model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private readonly BLOOD_API: string = "blood-data/by-page/";

  constructor(
    private http: HttpClient
  ) { }

  getAllBloodData(page: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get(environment.HOSPITAL_APP + this.BLOOD_API + page, {headers:headers});
  }

  getAllBloodDataForPatient(page: number, id: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get(environment.HOSPITAL_APP + this.BLOOD_API + page + "/by-patient/" + id, {headers:headers});
  }
}
