import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AlarmsModel } from '../models/alarms.model';
import { LogTypeModel } from '../models/log.type.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AlarmsService {

  private readonly createAlarmForLogType: string = "alarm/create-alarm-for-logs";
  private readonly createAlarmForDoSIP: string = "alarm/create-alarm-for-dos";

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  createAlarmForLog(data: LogTypeModel): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.post(environment.HOSPITAL_APP + this.createAlarmForLogType , data, {headers:headers});
  }

  createAlarmForDoS(data: AlarmsModel): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + this.authService.getToken()
    });
    return this.http.post(environment.HOSPITAL_APP + this.createAlarmForDoSIP , data, {headers:headers});
  }

}
