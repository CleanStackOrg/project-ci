package org.cleanstack.ci;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import org.cleanstack.ci.spi.Extension;

public class AppExtensionConfig {
  ServiceLoader<Extension> extensionsLoader = ServiceLoader.load(Extension.class);
  Map<String, Extension> extensions = new HashMap<String, Extension>();

  void initCommands() {
    load();
    refs();
    init();
  }

  private void load() {
    extensions.clear();
    extensionsLoader.reload();
  }

  private void init() {
    Consumer<Extension> init = new Consumer<Extension>() {
      public void accept(Extension extension) {
	extension.init();
      }
    };
    extensionsLoader.forEach(init);
  }

  private void refs() {
    Consumer<Extension> ref = new Consumer<Extension>() {
      public void accept(Extension extension) {
	extensions.put(extension.getId(), extension);
      }
    };
    extensionsLoader.forEach(ref);
  }
}
