import {Component, Input, OnInit} from '@angular/core';
import {AlarmModel} from '../../models/alarm.model';

@Component({
  selector: 'app-alarm',
  templateUrl: './alarm.component.html',
  styleUrls: ['./alarm.component.css']
})
export class AlarmComponent implements OnInit {

  @Input() message: AlarmModel;

  constructor() { }

  ngOnInit(): void {
  }

}
