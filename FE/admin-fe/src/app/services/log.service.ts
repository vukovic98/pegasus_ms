import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserDetails} from '../models/user.model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private readonly allLogs: string = "logs/by-page/";
  private readonly filterLogsAPI: string = "logs/filter-by-page/";


  constructor(private http: HttpClient) { }

  getAllLogs(page: number): Observable<any> {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
      });
      return this.http.get(environment.ADMIN_APP + this.allLogs + page, {headers:headers});
  }

  filterLogs(page: number, logs: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.post(environment.ADMIN_APP + this.filterLogsAPI + page, logs, {headers:headers});
  }
}
