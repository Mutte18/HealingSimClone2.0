package com.healing.spell.exceptions;

public class SpellOnCooldownException extends RuntimeException {
  public SpellOnCooldownException(String message) {
    System.err.println(message);
  }
}
