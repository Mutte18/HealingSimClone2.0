package com.healing.state;

import com.healing.state.response.StateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("state")
public class StateController {

  private StateService stateService;

  @Autowired
  public StateController(StateService stateService) {
    this.stateService = stateService;
  }

  @GetMapping(produces = "application/json")
  public ResponseEntity<StateResponse> getState() {
    var state = stateService.getState();
    var stateResponse = new StateResponse(state);
    return ResponseEntity.ok(stateResponse);
  }
}
