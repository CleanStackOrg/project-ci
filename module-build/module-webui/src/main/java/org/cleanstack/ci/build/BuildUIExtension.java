package org.cleanstack.ci.build;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.cleanstack.ci.spi.UIExtension;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class BuildUIExtension extends UIExtension {

  public String getId() {
    return "build-webui";
  }

  public String init() {

    ScriptEngine jsEngine = new ScriptEngineManager().getEngineByExtension("js");
    try {
      jsEngine.eval(Resources.toString(this.getClass().getClassLoader().getResource("app.js"), Charsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "build-webui init";
  }
}