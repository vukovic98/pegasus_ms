import { Component, OnInit } from '@angular/core';
import {DeviceService} from "../../../services/device.service";
import {PageEvent} from "@angular/material/paginator";

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

  constructor(private deviceService: DeviceService) { }

  ngOnInit(): void {
    this.deviceService.getAllNeuroData(this.pageIndex).subscribe(
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
