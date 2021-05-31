import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AlarmModel} from '../models/alarm.model';
import {environment} from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private readonly HEART_DEVICE_API: string = "heart-data/by-page/";
private readonly BLOOD_API: string = "blood-data/by-page/";
private readonly NEURO_DEVICE_API: string = "neuro-data/by-page/";
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

  getAllHeartData(page: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get<Array<any>>(environment.HOSPITAL_APP + this.HEART_DEVICE_API + page, {headers:headers});
  }

getAllNeuroData(page: number): Observable<any>{
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
  });
  return this.http.get<Array<any>>(environment.HOSPITAL_APP + this.NEURO_DEVICE_API + page, {headers:headers});
}}
