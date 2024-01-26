import {Component} from '@angular/core';
import {Socket} from 'ngx-socket-io';

@Component({
  selector: 'app-web-sockets',
  standalone: true,
  imports: [],
  templateUrl: './web-sockets.component.html',
  styleUrl: './web-sockets.component.scss'
})
export class WebSocketsComponent {
  constructor(private socket: Socket) {
    this.socket.emit('chat message')
    this.socket.fromEvent<string>('chat message').subscribe((message) => {
      console.log("Received message: ", message)
    });
  }

}
