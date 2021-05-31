import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material/paginator';
import {LogService} from '../../services/log.service';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit {

  length = 0;
  pageSize = 13;
  pageIndex = 0;
  showFirstLastButtons = true;
  public logs: Array<any> = [];

  private filterMode = false;
  private filterDto = null;

  filterForm = new FormGroup({
    regex: new FormControl(''),
    status: new FormControl('')
  });

  constructor(
    private logService: LogService
  ) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    if(!this.filterMode) {
      this.logService.getAllLogs(this.pageIndex).subscribe((response) => {
        this.logs = response.content;
        this.length = response.totalElements;
      })
    } else {
      this.logService.filterLogs(this.pageIndex, this.filterDto).subscribe((response) => {
        this.logs = response.content;
        this.length = response.totalElements;
      })
    }
  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageIndex = event.pageIndex;

    if(!this.filterMode) {
      this.logService.getAllLogs(this.pageIndex).subscribe((response) => {
        this.logs = response.content;
        this.length = response.totalElements;
      })
    }else {
      this.logService.filterLogs(this.pageIndex, this.filterDto).subscribe((response) => {
        this.logs = response.content;
        this.length = response.totalElements;
      })
    }
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
      console.log(this.filterMode)
    } else {
      this.pageIndex = 0;
      this.filterMode = false;
      this.getData();
      console.log(this.filterMode)
    }
  }

}
