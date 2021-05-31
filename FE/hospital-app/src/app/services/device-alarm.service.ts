import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {PatientModel} from "../models/patient.model";
import {AlarmModel} from "../models/alarm.model";

@Injectable({
  providedIn: 'root'
})
export class DeviceAlarmService {

  private readonly ALARM_API: string = "alarm/by-page/";

  constructor(
    private http: HttpClient
  ) { }

  getAll(page: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get<Array<AlarmModel>>(environment.HOSPITAL_APP + this.ALARM_API + page, {headers:headers});
  }

}
