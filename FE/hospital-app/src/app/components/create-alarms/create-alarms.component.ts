import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {PatientModel} from "../../models/patient.model";
import {PatientService} from "../../services/patient.service";
import {DeviceAlarmService} from "../../services/device-alarm.service";
import Swal from "sweetalert2";
import {CreateAlarmModel} from "../../models/create-alarm.model";
import {CombinedAlarmModel} from "../../models/combined.alarm.model";

@Component({
  selector: 'app-create-alarms',
  templateUrl: './create-alarms.component.html',
  styleUrls: ['./create-alarms.component.css']
})
export class CreateAlarmsComponent implements OnInit {

  public isCreating: boolean = false;
  public attrs = [];
  heart_attrs = ['saturation','systolic', 'diastolic','heartRate'];
  blood_attrs = ['CRP','leukocytes','erythrocytes','hemoglobin'];
  neuro_attrs = ['ICP','BIS'];
  device = 'heart';
  selected = '';

  alarmForm = new FormGroup({
    lower: new FormControl(''),
    upper: new FormControl(''),
    patientId: new FormControl(('')),
    alarmPurpose: new FormControl(('')),
  });
  combinedAlarmForm= new FormGroup({
    lower1: new FormControl(''),
    lower2: new FormControl(''),
    upper1: new FormControl(''),
    upper2: new FormControl(''),
    patientId: new FormControl(('')),
    alarmPurpose1: new FormControl(('')),
    alarmPurpose2: new FormControl(('')),
    device: new FormControl(('')),
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

  createNewCombinedAlarm() {
    let data: CombinedAlarmModel = {
      maxValue1 : this.combinedAlarmForm.controls["upper1"].value,
      maxValue2 : this.combinedAlarmForm.controls["upper2"].value,
      minValue1 :this.combinedAlarmForm.controls["lower1"].value,
      minValue2 :this.combinedAlarmForm.controls["lower2"].value,
      attrName1: this.combinedAlarmForm.controls["alarmPurpose1"].value,
      attrName2: this.combinedAlarmForm.controls["alarmPurpose2"].value,
      patientID: this.combinedAlarmForm.controls["patientId"].value
    };
    if(data.attrName1 != data.attrName2){
      this.isCreating = true;
      Swal.fire({
        text: "Pleas wait! An alarm is being created...",
        allowOutsideClick: false,
        allowEscapeKey: false,
        didOpen: () => {
          Swal.showLoading()}
      });
      switch (this.device){
        case 'heart': {
          this.alarmService.createCombinedAlarmHeart(data).subscribe((response) => {
              Swal.fire({
                icon: 'success',
                title: 'New alarm successfully created!',
                timer: 1900,
                showConfirmButton: false
              });
              this.isCreating = false;
              this.combinedAlarmForm.reset();
              this.device='heart';

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
        case 'neuro': {
          this.alarmService.createCombinedAlarmNeuro(data).subscribe((response) => {
              Swal.fire({
                icon: 'success',
                title: 'New alarm successfully created!',
                timer: 1900,
                showConfirmButton: false
              });
              this.isCreating = false;
              this.combinedAlarmForm.reset();
              this.device = 'neuro';

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
        case 'blood': {
          this.alarmService.createCombinedAlarmBlood(data).subscribe((response) => {
              Swal.fire({
                icon: 'success',
                title: 'New alarm successfully created!',
                timer: 1900,
                showConfirmButton: false
              });
              this.isCreating = false;
              this.combinedAlarmForm.reset();
              this.device = 'blood';
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
    else {
      Swal.fire({
        icon: 'error',
        title: 'You must combine different parameters.',
        confirmButtonColor: '#DC143C'
      });
    }
  }
}
