import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import { AlarmsService } from 'src/app/services/alarms.service';
import Swal from "sweetalert2";
import {LogTypeModel} from "../../models/log.type.model";

@Component({
  selector: 'app-create-alarm-for-logs',
  templateUrl: './create-alarm-for-logs.component.html',
  styleUrls: ['./create-alarm-for-logs.component.css']
})
export class CreateAlarmForLogsComponent implements OnInit {
  public isCreating: boolean = false;

  alarmForm = new FormGroup({
    logType: new FormControl(''),
  });


  constructor(private alarmsService: AlarmsService) { }

  ngOnInit(): void {
  }

  createNewAlarm() {
    let data: LogTypeModel = {
      logType : this.alarmForm.controls["logType"].value
    };
    this.isCreating = true;
    Swal.fire({
      text: "Please wait! An alarm is being created...",
      allowOutsideClick: false,
      allowEscapeKey: false,
      didOpen: () => {
        Swal.showLoading()}
    });
    this.alarmsService.createAlarmForLog(data).subscribe((response) => {
      Swal.fire({
        icon: 'success',
        title: 'New alarm for '+this.alarmForm.controls["logType"].value +' successfully created!',
        timer: 1900,
        showConfirmButton: false
      });
      this.isCreating = false;
      this.alarmForm.reset();
    })
  }
}
