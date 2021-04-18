import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {AddUserData, UserDetails} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly allAdmins: string = "admin/by-page/";
  private readonly allDoctors: string = "doctor/by-page/";
  private readonly changeRole: string = "users/change-authority";
  private readonly addUserEnd: string = "users";

  constructor(private http: HttpClient, private route: Router) { }

  getAllAdmins(page: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get<Array<UserDetails>>(environment.HOSPITAL_APP + this.allAdmins + page, {headers:headers});
  }

  getAllDoctors(page: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get<Array<UserDetails>>(environment.HOSPITAL_APP + this.allDoctors + page, {headers:headers});
  }

  changeAuthority(id: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post(environment.HOSPITAL_APP+ this.changeRole, id, {headers:headers});
  }

  addUser(data: AddUserData): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post(environment.HOSPITAL_APP+ this.addUserEnd, data, {headers:headers});
  }
}
