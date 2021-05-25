import { Component, OnInit } from '@angular/core';
import {WebSocketAPI} from '../../services/alarm.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  public certified: boolean = true;
  public messages: Array<any> = [];

  private webSocketAPI: WebSocketAPI;

  constructor(
  ) { }

  ngOnInit(): void {
    this.webSocketAPI = new WebSocketAPI(new AdminPanelComponent());
    this.connect();
  }

  connect(){
    this.webSocketAPI._connect();
  }

  disconnect(){
    this.webSocketAPI._disconnect();
  }


  handleMessage(message){
    let data: boolean = message.includes("{");
    if(data) {
      let index = message.indexOf("{");

      let messData = JSON.parse(message.substr(index-1));

      console.log(messData)
      this.messages.push(messData);

    }
  }

}
