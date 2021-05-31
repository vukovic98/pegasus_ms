import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {PatientModel} from "../../models/patient.model";
import {PageEvent} from "@angular/material/paginator";
import {DeviceAlarmService} from "../../services/device-alarm.service";
import {AlarmModel} from "../../models/alarm.model";

@Component({
  selector: 'app-alarms',
  templateUrl: './alarms.component.html',
  styleUrls: ['./alarms.component.css']
})
export class AlarmsComponent implements OnInit {

  constructor(private dialog: MatDialog, private alarmService: DeviceAlarmService) { }

  public alarms : Array<AlarmModel> =  [];
  p_length = 0;
  pageSize = 8;
  pageIndex = 0;
  showFirstLastButtons = true;

  ngOnInit(): void {
    this.alarmService.getAll(this.pageIndex).subscribe(
      (alarms) => {
        this.alarms = alarms.content;
        this.p_length= alarms.totalElements;
      }
    )
  }

  handlePageEvent(event: PageEvent) {
    this.p_length = event.length;
    this.pageIndex = event.pageIndex;

    this.alarmService.getAll(this.pageIndex).subscribe((response) => {
      this.alarms = response.content;
      this.p_length = response.totalElements;
    })}
}
