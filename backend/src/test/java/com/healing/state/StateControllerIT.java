package com.healing.state;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.healing.entity.EntityRole;
import com.healing.gamelogic.Game;
import com.healing.gamelogic.RaiderHandler;
import com.healing.integration.IntegrationTest;
import com.healing.state.model.StateModel;
import com.healing.state.response.StateResponse;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class StateControllerIT extends IntegrationTest {
  @Autowired Game game;
  @Autowired RaiderHandler raiderHandler;
  private WebSocketStompClient stompClient;
  @Autowired
  private SimpMessagingTemplate messagingTemplate;


  @BeforeEach
  public void setUp() {
    stompClient = new WebSocketStompClient(new SockJsClient(
            Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
  }

  @Test
  void shouldReturnCurrentState() throws Exception {
    raiderHandler.getPlayer().reduceHealth(50);

    var result =
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/state").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    var stateResponse =
        mapper.readValue(result.getResponse().getContentAsString(), StateResponse.class);

    Assertions.assertEquals(20, stateResponse.getState().getRaidGroup().size());
    Assertions.assertEquals(
        50,
        stateResponse.getState().getRaidGroup().stream()
            .filter(raider -> raider.getRole().equals(EntityRole.PLAYER))
            .findFirst()
            .get()
            .getHealth());

    System.out.println(stateResponse.getState());
  }

  @Test
  void testWebSocketEndpoint() throws ExecutionException, InterruptedException {
    StompSession session = stompClient.connect("ws://localhost:8080/healing-simulator", new StompSessionHandlerAdapter() {
    }).get();
    session.subscribe("/game/state", new StompFrameHandler() {
      @Override
      public Type getPayloadType(StompHeaders stompHeaders) {
        return null;
      }

      @Override
      public void handleFrame(StompHeaders stompHeaders, Object o) {
        var stateResponse = (StateResponse) o;
      }
    });
    messagingTemplate.convertAndSend("/game/state", new StateModel());

  }
}
