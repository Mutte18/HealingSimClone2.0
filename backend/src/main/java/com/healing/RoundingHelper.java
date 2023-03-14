package com.healing;

public class RoundingHelper {
  public static double roundToOneDecimal(double value) {
    return Math.round(value * 100.0) / 100.0;
  }
}
