package com.kh.bookJeokBookJeok.stub;

public class RandomGenerator {
  public static String randomStringGenerator() {
    StringBuilder sb = new StringBuilder();
    int length = (int)(Math.random() * 5 + 5);
    for (int i = 0; i < length; i++) {
      sb.append((char)('a' + (int)(Math.random() * 26)));
    }
    return sb.toString();
  }
}
