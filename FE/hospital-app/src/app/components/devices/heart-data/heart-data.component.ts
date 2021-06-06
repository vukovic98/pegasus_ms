import { Component, OnInit } from '@angular/core';
import {DeviceService} from "../../../services/device.service";
import {PageEvent} from "@angular/material/paginator";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-heart-data',
  templateUrl: './heart-data.component.html',
  styleUrls: ['./heart-data.component.css']
})
export class HeartDataComponent implements OnInit {

  length = 0;
  pageSize = 13;
  pageIndex = 0;
  showFirstLastButtons = true;
  public data: Array<any> = [];
  filterMode: boolean = false;

  filterForm = new FormGroup({
    patientID: new FormControl('')
  });

  constructor(
    private deviceService: DeviceService
  ) { }

  ngOnInit(): void {

    this.getData();
  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageIndex = event.pageIndex;


  }

  getData() {
    if(this.filterMode) {
      this.deviceService.getAllHeartDataForPatient(this.pageIndex, this.filterForm.value.patientID).subscribe((response) => {
        this.data = response.content;
        this.length = response.totalElements;
      })
    } else {
      this.deviceService.getAllHeartData(this.pageIndex).subscribe(
        (data) => {
          this.data = data.content;
          this.pageSize= data.totalElements;
        }
      )
    }
  }

  filterByPatient() {
    if (this.filterForm.value.patientID != "" && this.filterForm.value.patientID != null) {
      this.filterMode = true;
      this.pageIndex = 0;
      this.getData();
    } else {
      this.pageIndex = 0;
      this.filterMode = false;
      this.getData();
    }
  }

}
