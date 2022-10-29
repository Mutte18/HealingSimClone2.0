package com.healing.state;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.healing.gamelogic.Game;
import com.healing.gamelogic.RaiderHandler;
import com.healing.integration.IntegrationTest;
import com.healing.state.response.StateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class StateControllerIT extends IntegrationTest {
  @Autowired Game game;
  @Autowired RaiderHandler raiderHandler;

  @Test
  void shouldReturnCurrentState() throws Exception {
    // raiderHandler.getPlayer().get().reduceHealth(50);

    var result =
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/state").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    // writeValueAsString(new StateResponse(result.getResponse().getContentAsString())
    System.out.println(result.getResponse().getContentAsString());

    var stateResponse =
        objectMapper.readValue(result.getResponse().getContentAsString(), StateResponse.class);

    System.out.println(stateResponse);
  }
}
