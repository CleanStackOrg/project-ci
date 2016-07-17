package org.cleanstack.ci.build;

import org.cleanstack.ci.spi.Extension;

public class UIExtension extends Extension {

	public String getId() {
		return "build-webui";
	}

	public String init() {
		return "build-webui init";
	}
}