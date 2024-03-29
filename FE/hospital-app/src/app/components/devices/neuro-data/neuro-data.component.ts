import { Component, OnInit } from '@angular/core';
import {DeviceService} from "../../../services/device.service";
import {PageEvent} from "@angular/material/paginator";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-neuro-data',
  templateUrl: './neuro-data.component.html',
  styleUrls: ['./neuro-data.component.css']
})
export class NeuroDataComponent implements OnInit {
  length = 0;
  pageSize = 13;
  pageIndex = 0;
  showFirstLastButtons = true;
  public data: Array<any> = [];
  filterMode: boolean = false;

  filterForm = new FormGroup({
    patientID: new FormControl('')
  });

  constructor(private deviceService: DeviceService) { }

  ngOnInit(): void {
    this.deviceService.getAllNeuroData(this.pageIndex).subscribe(
      (data) => {
        this.data = data.content;
        this.length= data.totalElements;
      }
    )
  }
  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageIndex = event.pageIndex;
    this.getData();
  }
  getData() {
    if(this.filterMode) {
      this.deviceService.getAllNeuroDataForPatient(this.pageIndex, this.filterForm.value.patientID).subscribe((response) => {
        this.data = response.content;
        this.length = response.totalElements;
      })
    } else {
      this.deviceService.getAllNeuroData(this.pageIndex).subscribe(
        (data) => {
          this.data = data.content;
          this.length = data.totalElements;
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
