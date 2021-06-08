import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {PatientModel} from "../../models/patient.model";
import {PatientService} from "../../services/patient.service";
import {DeviceAlarmService} from "../../services/device-alarm.service";
import Swal from "sweetalert2";
import {CreateAlarmModel} from "../../models/create-alarm.model";

@Component({
  selector: 'app-create-alarms',
  templateUrl: './create-alarms.component.html',
  styleUrls: ['./create-alarms.component.css']
})
export class CreateAlarmsComponent implements OnInit {

  public isCreating: boolean = false;

  alarmForm = new FormGroup({
    lower: new FormControl(''),
    upper: new FormControl(''),
    patientId: new FormControl(('')),
    alarmPurpose: new FormControl(('')),
  });


  public patients: Array<PatientModel> = [];

  constructor(private patientService: PatientService,
              private alarmService: DeviceAlarmService) { }

  ngOnInit(): void {
    this.patientService.getAllPatients().subscribe((data) => {
      this.patients = data
    },
      error => { console.log(error)})


  }
  clearForm(){
    this.alarmForm.clearValidators();
  }
  createNewAlarm() {
    if((this.alarmForm.controls["upper"].value == '' || this.alarmForm.controls["upper"].value == null )&& (this.alarmForm.controls["lower"].value == '' || this.alarmForm.controls["lower"].value == null ) ){
      Swal.fire({
        icon: 'error',
        title: 'Unable to create new alarm',
        text: 'Please make sure that at least one of bound fields are not empty',
        confirmButtonColor: '#DC143C'
      });
    } else {
          let data: CreateAlarmModel = {
            maxValue : this.alarmForm.controls["upper"].value,
            minValue :this.alarmForm.controls["lower"].value,
            patientID: this.alarmForm.controls["patientId"].value
          };
          switch (this.alarmForm.controls["alarmPurpose"].value) {
            case "crp" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForCrp(data).subscribe((response) => {
                Swal.fire({
                  icon: 'success',
                  title: 'New alarm for CRP successfully created!',
                  timer: 1900,
                  showConfirmButton: false
                });
                this.isCreating = false;
                this.alarmForm.reset();
                this.clearForm();

              },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "eryth" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForErythrocytes(data).subscribe((response) => {

                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for erythrocytes successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  });
                  this.isCreating = false;
                  this.alarmForm.reset();
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "leu" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForLeukocytes(data).subscribe((response) => {

                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for leukocytes successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  });
                  this.isCreating = false;
                  this.alarmForm.reset();
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "hem" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForHemoglobin(data).subscribe((response) => {

                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for hemoglobin successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  });
                  this.isCreating = false;
                  this.alarmForm.reset();
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "rate" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForHeartRate(data).subscribe((response) => {

                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for heart rate successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  });
                  this.isCreating = false;
                  this.alarmForm.reset();
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "sat" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForSaturation(data).subscribe((response) => {
                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for saturation successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  });
                  this.isCreating = false;
                  this.alarmForm.reset();
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "sys" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForSystolic(data).subscribe((response) => {

                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for systolic pressure successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  });
                  this.isCreating = false;
                  this.alarmForm.reset();
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "dia" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForDiastolic(data).subscribe((response) => {

                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for diastolic pressure successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  })
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "bis" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForBis(data).subscribe((response) => {

                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for BIS successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  })
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
            case "icp" : {
              this.isCreating = true;
              Swal.fire({
                text: "Pleas wait! An alarm is being created...",
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen: () => {
                  Swal.showLoading()}
              });
              this.alarmService.createAlarmForIcp(data).subscribe((response) => {
                  Swal.fire({
                    icon: 'success',
                    title: 'New alarm for icp successfully created!',
                    timer: 1900,
                    showConfirmButton: false
                  })
                },
                error => {
                  Swal.fire({
                    icon: 'error',
                    title: 'Unable to create new alarm',
                    confirmButtonColor: '#DC143C'
                  });
                });
              break;
            }
          }
    }
  }
}
