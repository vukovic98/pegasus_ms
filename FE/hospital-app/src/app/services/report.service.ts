import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from './auth.service';
import {DeviceLogModel, LogModel, ReportEntryModel} from '../models/report.model';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {AlarmModel, SecurityAlarm} from '../models/alarm.model';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private readonly REPORT_LOGS_API: string = "report/log-data";
  private readonly REPORT_DEVICE_API: string = "report/device-data";
  private readonly REPORT_DEV_ALARM_API: string = "report/device-alarm";
  private readonly REPORT_LOG_ALARM_API: string = "report/log-alarm";

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getAllLogs(data: ReportEntryModel): Observable<Array<LogModel>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authService.getToken()
    });
    return this.http.post<Array<LogModel>>(environment.HOSPITAL_APP + this.REPORT_LOGS_API, data, {headers: headers});
  }

  getAllDeviceData(data: ReportEntryModel): Observable<Array<DeviceLogModel>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authService.getToken()
    });
    return this.http.post<Array<DeviceLogModel>>(environment.HOSPITAL_APP + this.REPORT_DEVICE_API, data, {headers: headers});
  }

  getAllLogAlarms(data: ReportEntryModel): Observable<Array<SecurityAlarm>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authService.getToken()
    });
    return this.http.post<Array<SecurityAlarm>>(environment.HOSPITAL_APP + this.REPORT_LOG_ALARM_API, data, {headers: headers});
  }

  getAllDeviceAlarms(data: ReportEntryModel): Observable<Array<AlarmModel>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authService.getToken()
    });
    return this.http.post<Array<AlarmModel>>(environment.HOSPITAL_APP + this.REPORT_DEV_ALARM_API, data, {headers: headers});
  }
}
