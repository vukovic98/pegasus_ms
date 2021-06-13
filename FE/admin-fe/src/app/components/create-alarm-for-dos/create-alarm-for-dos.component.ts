import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AlarmsModel } from 'src/app/models/alarms.model';
import { AlarmsService } from 'src/app/services/alarms.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-alarm-for-dos',
  templateUrl: './create-alarm-for-dos.component.html',
  styleUrls: ['./create-alarm-for-dos.component.css']
})
export class CreateAlarmForDosComponent implements OnInit {
  public isCreating: boolean = false;

  alarmForm = new FormGroup({
    ipAddress: new FormControl(''),
    noRequests: new FormControl('')
  });

  constructor(private alarmsService: AlarmsService) { }

  ngOnInit(): void {
  }

  createNewAlarm() {
    let data: AlarmsModel = {
      ipAddress : this.alarmForm.controls["ipAddress"].value,
      noRequests : this.alarmForm.controls["noRequests"].value
    };
    this.isCreating = true;
    Swal.fire({
      text: "Please wait! An alarm is being created...",
      allowOutsideClick: false,
      allowEscapeKey: false,
      didOpen: () => {
        Swal.showLoading()}
    });
    this.alarmsService.createAlarmForDoS(data).subscribe((response) => {
      Swal.fire({
        icon: 'success',
        title: 'New alarm for '+this.alarmForm.controls["ipAddress"].value +' successfully created!',
        timer: 1900,
        showConfirmButton: false
      });
      this.isCreating = false;
      this.alarmForm.reset();
    })
  }

}
