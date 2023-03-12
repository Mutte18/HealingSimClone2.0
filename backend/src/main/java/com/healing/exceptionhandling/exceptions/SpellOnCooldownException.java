package com.healing.exceptionhandling.exceptions;

public class SpellOnCooldownException extends RuntimeException {
  public SpellOnCooldownException(String message) {
    System.err.println(message);
  }
}
