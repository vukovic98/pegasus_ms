import { Component, OnInit } from '@angular/core';
import {PatientDetailsComponent} from "../patientDetails/patient-details/patient-details.component";
import {PatientModel} from "../../../models/patient.model";
import {MatDialog} from "@angular/material/dialog";
import {PatientService} from "../../../services/patient.service";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

  constructor(private dialog: MatDialog, private patientService: PatientService) { }

  public patients : Array<PatientModel> =  [];

  p_length = 0;
  pageSize = 8;
  pageIndex = 0;
  showFirstLastButtons = true;

  ngOnInit(): void {

    this.patientService.getAll(this.pageIndex).subscribe(
      (patients) => {
        console.log((patients.content))
        this.patients = patients.content;
        this.p_length= patients.totalElements;
        console.log(this.p_length)
      }
    )
  }

  handlePageEvent(event: PageEvent) {
    this.p_length = event.length;
    this.pageIndex = event.pageIndex;

    this.patientService.getAll(this.pageIndex).subscribe((response) => {
      this.patients = response.content;
      this.p_length = response.totalElements;
    })

  }



  private patientModel: PatientModel = {
    id: 0,
    gender:'',
    name:'',
    birthday:'',
    currentAge: 0,
    height:0,
    weight:0,
    averageBloodPressure:'',
    pastMedicalRecord: ''
  }

  openMedicalRecord(id: number) {
    this.patientModel = this.patients.filter(patient => patient.id === id)[0];
    this.dialog.open(PatientDetailsComponent, {
      width: '650px',
      height: '530px',
      data: this.patientModel
    });

  }
}
