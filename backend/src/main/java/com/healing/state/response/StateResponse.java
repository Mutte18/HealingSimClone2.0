package com.healing.state.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healing.state.model.StateModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateResponse {
  private StateModel state;

  public StateResponse(@JsonProperty("state") StateModel state) {
    this.state = state;
  }
}
