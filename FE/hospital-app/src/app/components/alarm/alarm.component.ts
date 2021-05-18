import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-alarm',
  templateUrl: './alarm.component.html',
  styleUrls: ['./alarm.component.css']
})
export class AlarmComponent implements OnInit {

  @Input() message: String;

  constructor() { }

  ngOnInit(): void {
    console.log(this.message)
  }

}
