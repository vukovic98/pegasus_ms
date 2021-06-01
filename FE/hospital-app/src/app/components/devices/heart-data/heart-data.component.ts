import { Component, OnInit } from '@angular/core';
import {DeviceService} from "../../../services/device.service";
import {PageEvent} from "@angular/material/paginator";

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

  constructor(
    private deviceService: DeviceService
  ) { }

  ngOnInit(): void {
    this.deviceService.getAllHeartData(this.pageIndex).subscribe(
      (data) => {
        this.data = data.content;
        this.pageSize= data.totalElements;
      }
    )
  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageIndex = event.pageIndex;


  }

}
