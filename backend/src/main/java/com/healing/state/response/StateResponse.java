package com.healing.state.response;

import com.healing.state.model.StateModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateResponse {
  private StateModel state;

  public StateResponse(StateModel state) {
    this.state = state;
  }

  // Convert Model to Response
}
