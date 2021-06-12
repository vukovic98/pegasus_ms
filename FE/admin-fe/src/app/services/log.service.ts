import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {LogTypeModel} from "../models/log.type.model";
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private readonly allLogs: string = "logs/by-page/";
  private readonly filterLogsAPI: string = "logs/filter-by-page/";
  private readonly hospitalLogs: string = "logs/hospital-logs/by-page/";
  private readonly hospitalBloodLogs: string = "logs/hospital-logs/device-logs/blood-logs/by-page/";
  private readonly hospitalHeartLogs: string = "logs/hospital-logs/device-logs/heart-logs/by-page/";
  private readonly hospitalNeuroLogs: string = "logs/hospital-logs/device-logs/neuro-logs/by-page/";
  private readonly filterHospitalLogs: string = "logs/hospital-logs/filter-by-page/";
  private readonly createAlarmForLogType: string = "logs/create-alarm-for-logs";

  constructor(
    private http: HttpClient,
    private authService: AuthService
    ) { }

  getAllLogs(page: number): Observable<any> {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization' : 'Bearer ' + this.authService.getToken()
      });
      return this.http.get(environment.ADMIN_APP + this.allLogs + page, {headers:headers});
  }

  getAllHospitalLogs(page: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.get(environment.ADMIN_APP + this.hospitalLogs + page, {headers:headers});
  }

  getAllBloodLogs(page: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.get(environment.ADMIN_APP + this.hospitalBloodLogs + page, {headers:headers});
  }

  getAllHeartLogs(page: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.get(environment.ADMIN_APP + this.hospitalHeartLogs + page, {headers:headers});
  }

  getAllNeuroLogs(page: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.get(environment.ADMIN_APP + this.hospitalNeuroLogs + page, {headers:headers});
  }

  filterLogs(page: number, logs: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.post(environment.ADMIN_APP + this.filterLogsAPI + page, logs, {headers:headers});
  }

  filterAllHospitalLogs(page: number, logs: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.post(environment.ADMIN_APP + this.filterHospitalLogs + page, logs, {headers:headers});
  }

  createAlarmForLog(data: LogTypeModel): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.post(environment.HOSPITAL_APP + this.createAlarmForLogType , data, {headers:headers});
  }
}
