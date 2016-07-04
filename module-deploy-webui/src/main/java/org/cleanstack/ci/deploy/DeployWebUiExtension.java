package org.cleanstack.ci.deploy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.cleanstack.ci.spi.Extension;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class DeployWebUiExtension extends Extension {

	public String getId() {
		return "deploy-webui";
	}

	public String init() {

		ScriptEngine jsEngine = new ScriptEngineManager()
				.getEngineByExtension("js");
		try {
			jsEngine.eval(Resources.toString(this.getClass().getClassLoader()
					.getResource("app.js"), Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "hello";
	}
}