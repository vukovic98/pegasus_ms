import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {LoginModel, loginResponse, TokenModel} from '../models/auth.model';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {UserData} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly ENDPOINT_LOGIN: string = "auth/log-in"

  constructor(private http: HttpClient, private route: Router) { }

  login(data: LoginModel): Observable<loginResponse> {

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    let message = JSON.stringify(data);

    return this.http.post<loginResponse>(environment.HOSPITAL_APP + this.ENDPOINT_LOGIN, message, { headers: headers });
  }

  getToken() {
    return localStorage.getItem("accessToken");
  }

  decodeToken(token: string): TokenModel | null {
    if (token) {
      let payload = token.split(".")[1];
      payload = window.atob(payload);
      return JSON.parse(payload);
    } else return null;
  }

  getHospitalId(): string {
    let token = this.getToken();
    let data = this.decodeToken(token);

    return data.hospital;
  }
}
