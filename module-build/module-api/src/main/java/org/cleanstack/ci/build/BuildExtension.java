package org.cleanstack.ci.build;

import org.cleanstack.ci.spi.Extension;

public class BuildExtension extends Extension {

	public String getId() {
		return "build-api";
	}

	public String init() {
		return "build-api init";
	}
}