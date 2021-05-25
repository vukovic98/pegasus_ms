import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {AdminPanelComponent} from '../components/admin-panel/admin-panel.component';

export class WebSocketAPI {
  webSocketEndPoint: string = 'https://localhost:8081/ws';
  topic: string = "/topic";
  stompClient: any;

  messages: [];

  appComponent: AdminPanelComponent;
  constructor(appComponent: AdminPanelComponent){
    this.appComponent = appComponent;
  }
  _connect() {
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    const _this = this;
    _this.stompClient.connect({}, function (frame) {
      _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
        _this.onMessageReceived(sdkEvent);
      });
      //_this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  };

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error) {
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message
   */
  _send(message) {
    this.stompClient.send("/app/hello", {}, JSON.stringify(message));
  }

  onMessageReceived(message) {
    this.appComponent.handleMessage(JSON.stringify(message.body));
  }
}
