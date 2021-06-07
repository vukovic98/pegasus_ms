import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {PatientModel} from "../models/patient.model";
import {AlarmModel} from "../models/alarm.model";
import {CreateAlarmModel} from "../models/create-alarm.model";

@Injectable({
  providedIn: 'root'
})
export class DeviceAlarmService {

  private readonly ALARM_API: string = "alarm/by-page/";
  private readonly CREATE_ALARM_BLOOD_API: string = "blood-data/create-alarm-for-";
  private readonly CREATE_ALARM_NEURO_API: string = "neuro-data/create-alarm-for-";
  private readonly CREATE_ALARM_HEART_API: string = "heart-data/create-alarm-for-";

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

  createAlarmForCrp(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_BLOOD_API + 'crp', message, {headers:headers});
  }

  createAlarmForLeukocytes(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_BLOOD_API + 'leukocytes', message, {headers:headers});
  }

  createAlarmForErythrocytes(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_BLOOD_API + 'erythrocytes', message, {headers:headers});
  }

  createAlarmForHemoglobin(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_BLOOD_API + 'hemoglobin', message, {headers:headers});
  }
  createAlarmForSaturation(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_HEART_API + 'saturation', message, {headers:headers});
  }

  createAlarmForHeartRate(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_HEART_API + 'heart-rate', message, {headers:headers});
  }

  createAlarmForSystolic(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_HEART_API + 'systolic', message, {headers:headers});
  }

  createAlarmForDiastolic(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_HEART_API + 'diastolic', message, {headers:headers});
  }

  createAlarmForBis(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_NEURO_API + 'bis', message, {headers:headers});
  }

  createAlarmForIcp(data: CreateAlarmModel){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    let message = JSON.stringify(data);
    return this.http.post(environment.HOSPITAL_APP + this.CREATE_ALARM_NEURO_API + 'icp', message, {headers:headers});
  }
}
