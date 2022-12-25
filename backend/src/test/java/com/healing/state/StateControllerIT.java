package com.healing.state;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.healing.gamelogic.Game;
import com.healing.gamelogic.RaiderHandler;
import com.healing.integration.IntegrationTest;
import com.healing.state.response.StateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class StateControllerIT extends IntegrationTest {
  @Autowired Game game;
  @Autowired RaiderHandler raiderHandler;

  @Test
  void shouldReturnCurrentState() throws Exception {
    raiderHandler.getPlayer().get().reduceHealth(50);

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
            .filter(raider -> raider.getRole().equals("PLAYER"))
            .findFirst()
            .get()
            .getHealth());

    System.out.println(stateResponse.getState());
  }
}
