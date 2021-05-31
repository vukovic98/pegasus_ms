import { Component, OnInit } from '@angular/core';
import {PageEvent} from '@angular/material/paginator';
import {DeviceService} from '../../../services/device.service';

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
  public data: Array<any> = [];

  constructor(
    private deviceService: DeviceService
  ) { }

  ngOnInit(): void {
  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageIndex = event.pageIndex;

    
  }

}
