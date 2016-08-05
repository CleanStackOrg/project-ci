package org.cleanstack.ci;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.cleanstack.ci.spi.UIExtension;

public class UIExtensionServlet extends HttpServlet {

  private static final long serialVersionUID = -3002548320788494016L;

  ServiceLoader<UIExtension> extensionsLoader = ServiceLoader.load(UIExtension.class);
  Map<String, UIExtension> extensions = new HashMap<String, UIExtension>();
  
  void initCommands() {
    load();
    refs();
    init2();
  }

  private void load() {
    extensions.clear();
    extensionsLoader.reload();
  }

  private void init2() {
    Consumer<UIExtension> init = new Consumer<UIExtension>() {
      public void accept(UIExtension extension) {
	extension.init();
      }
    };
    extensionsLoader.forEach(init);
  }

  private void refs() {
    Consumer<UIExtension> ref = new Consumer<UIExtension>() {
      public void accept(UIExtension extension) {
	extensions.put(extension.getId(), extension);
      }
    };
    extensionsLoader.forEach(ref);
  }

  
  @Override
  public void init() throws ServletException {
    super.init();
    
    System.out.println("hi it's webui extension servlet!");
    
    initCommands();
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_ACCEPTED);
    // TODO build modules' js aggregate
    // set content
  }
  
}
