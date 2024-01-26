package com.healing.websockets;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Controller
public class WebSocketsController {

  public WebSocketsController() throws ExecutionException, InterruptedException {
  WebSocketClient webSocketClient = new StandardWebSocketClient();
  WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
  stompClient.setMessageConverter(new MappingJackson2MessageConverter());

  StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
      // Send your message when the WebSocket connection is established
      session.send("/app/game", "Hello, WebSocket!");
    }
  };
    StompSession session = stompClient.connect("ws://localhost:8080/game", sessionHandler).get();



  }
  @MessageMapping ("/game")
  @SendTo ("/topic/chat")
  public Message sendMessage(Message message) {
    System.out.println("Hej?");
    return message;
  }
}
