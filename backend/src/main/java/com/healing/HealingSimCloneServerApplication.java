package com.healing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealingSimCloneServerApplication {

  public static void main(String[] args) {
    System.setProperty("java.awt.headless", "false");
    SpringApplication.run(HealingSimCloneServerApplication.class, args);
  }
}
