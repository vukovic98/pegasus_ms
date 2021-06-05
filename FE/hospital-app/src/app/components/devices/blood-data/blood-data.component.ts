import { Component, OnInit } from '@angular/core';
import {PageEvent} from '@angular/material/paginator';
import {DeviceService} from '../../../services/device.service';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-blood-data',
  templateUrl: './blood-data.component.html',
  styleUrls: ['./blood-data.component.css']
})
export class BloodDataComponent implements OnInit {

  length = 0;
  pageSize = 13;
  pageIndex = 0;
  showFirstLastButtons = true;
  filterMode: boolean = false;
  public data: Array<any> = [];

  filterForm = new FormGroup({
    patientID: new FormControl('')
  });

  constructor(
    private deviceService: DeviceService
  ) { }

  ngOnInit(): void {
    this.deviceService.getAllBloodData(this.pageIndex).subscribe((response) => {
      this.data = response.content;
      this.length = response.totalElements;
    })

  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageIndex = event.pageIndex;

    this.getData();
  }

  getData() {
    if(this.filterMode) {
      this.deviceService.getAllBloodDataForPatient(this.pageIndex, this.filterForm.value.patientID).subscribe((response) => {
        this.data = response.content;
        this.length = response.totalElements;
      })
    } else {
      this.deviceService.getAllBloodData(this.pageIndex).subscribe((response) => {
        this.data = response.content;
        this.length = response.totalElements;
      })
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
