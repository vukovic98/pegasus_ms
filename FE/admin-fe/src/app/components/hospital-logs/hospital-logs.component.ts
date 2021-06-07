import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {LogService} from '../../services/log.service';
import {PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-hospital-logs',
  templateUrl: './hospital-logs.component.html',
  styleUrls: ['./hospital-logs.component.css']
})
export class HospitalLogsComponent implements OnInit {

  length = 0;
  pageSize = 12;
  pageIndex = 0;
  showFirstLastButtons = true;
  public logs: Array<any> = [];

  private filterMode = false;
  private filterDto = null;

  filterForm = new FormGroup({
    regex: new FormControl(''),
    status: new FormControl('')
  });

  logSourceForm = new FormGroup({
    source: new FormControl('HOSPITAL')
  });

  constructor(
    private logService: LogService
  ) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    if(!this.filterMode) {
      if(this.logSourceForm.value.source === 'HOSPITAL') {
        this.logService.getAllHospitalLogs(this.pageIndex).subscribe((response) => {
          this.logs = response.content;
          this.length = response.totalElements;
        })
      }
      if(this.logSourceForm.value.source === 'BLOOD') {
        this.logService.getAllBloodLogs(this.pageIndex).subscribe((response) => {
          this.logs = response.content;
          this.length = response.totalElements;
        })
      }
      if(this.logSourceForm.value.source === 'HEART') {
        this.logService.getAllHeartLogs(this.pageIndex).subscribe((response) => {
          this.logs = response.content;
          this.length = response.totalElements;
        })
      }
      if(this.logSourceForm.value.source === 'NEURO') {
        this.logService.getAllNeuroLogs(this.pageIndex).subscribe((response) => {
          this.logs = response.content;
          this.length = response.totalElements;
        })
      }
    } else {
      this.logService.filterAllHospitalLogs(this.pageIndex, this.filterDto).subscribe((response) => {
        this.logs = response.content;
        this.length = response.totalElements;
      })
    }
  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageIndex = event.pageIndex;

    this.getData();
  }

  filter() {
    if (this.filterForm.value.regex != "" || this.filterForm.value.status != "") {
      this.filterDto = {
        regex: this.filterForm.value.regex,
        status: this.filterForm.value.status
      };
      this.filterMode = true;
      this.pageIndex = 0;
      this.getData();
    } else {
      this.pageIndex = 0;
      this.filterMode = false;
      this.getData();
    }
  }


  selectSource() {
    this.pageIndex = 0;
    this.getData();
  }
}
