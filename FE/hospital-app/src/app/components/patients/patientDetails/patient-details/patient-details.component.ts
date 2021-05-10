import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PatientModel} from "../../../../models/patient.model";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<PatientDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) public patient: PatientModel) { }

  ngOnInit(): void {
  }

}
