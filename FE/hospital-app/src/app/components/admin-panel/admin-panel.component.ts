import { BindingScope } from '@angular/compiler/src/render3/view/template';
import { ApplicationRef, Component, OnInit } from '@angular/core';
import { $ } from 'protractor';
import {WebSocketAPI} from '../../services/alarm.service';
import {AlarmModel} from '../../models/alarm.model';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  public certified: boolean = true;
  public messages: Array<AlarmModel> = [];

  private webSocketAPI: WebSocketAPI;

  constructor(
    private appRef: ApplicationRef
  ) { }

  ngOnInit(): void {
    this.webSocketAPI = new WebSocketAPI(this);
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
      let newMess = message.substr(index-1);
      let messData: any = JSON.parse(JSON.parse(newMess));

      console.log(messData)
      this.messages.unshift(messData);

      if (this.messages.length == 9) {
        this.messages.pop();
      }

      this.appRef.tick()

    }
  }

}
