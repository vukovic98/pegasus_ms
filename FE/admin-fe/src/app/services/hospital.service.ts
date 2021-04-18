import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {UserDetails} from '../models/user.model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  private readonly allHospitalsEnd: string = "hospital";

  constructor(private http: HttpClient, private route: Router) { }

  getAllHospitals(): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get(environment.HOSPITAL_APP + this.allHospitalsEnd, {headers:headers});
  }
}
