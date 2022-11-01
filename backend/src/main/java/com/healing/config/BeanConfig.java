package com.healing.config;

import com.healing.gamelogic.ActionsQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {
  /*@Bean
  @Scope("singleton")
  public RaiderHandler raiderHandlerSingleton() {
    var kalle = "Hej";
    return new RaiderHandler();
  }

   */

  @Bean
  @Scope("singleton")
  public ActionsQueue actionsQueueSingleton() {
    return new ActionsQueue();
  }
}
