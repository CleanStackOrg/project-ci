package org.cleanstack.ci.spi;

public abstract class Extension {
  public abstract String[] tags();
  public abstract String getId();
  public abstract String init();
}