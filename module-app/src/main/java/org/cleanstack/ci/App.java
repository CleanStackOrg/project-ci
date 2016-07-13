package org.cleanstack.ci;

public class App {
  public static void main(String[] args) {
    AppExtensionConfig cfg = new AppExtensionConfig();
    cfg.initCommands();
    
    for (String key: cfg.extensions.keySet()) {
      System.out.println(key);
    }
  }
}
