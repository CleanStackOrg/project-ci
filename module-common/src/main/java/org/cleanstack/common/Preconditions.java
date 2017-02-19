package org.cleanstack.common;

public class Preconditions {

  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }

  // TODO public static <T> T checkNotEmpty(T reference)

}
