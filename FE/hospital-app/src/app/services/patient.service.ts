import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {PatientModel} from "../models/patient.model";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private readonly PATIENT_API: string = "patient/by-page/";
  private readonly PATIENT_ALL_API: string = "patient/all";

  constructor(
    private http: HttpClient
  ) { }

  getAll(page: number): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get<Array<PatientModel>>(environment.HOSPITAL_APP + this.PATIENT_API + page, {headers:headers});
  }

  getAllPatients(): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")
    });
    return this.http.get<Array<PatientModel>>(environment.HOSPITAL_APP + this.PATIENT_ALL_API, {headers:headers});
  }

}
