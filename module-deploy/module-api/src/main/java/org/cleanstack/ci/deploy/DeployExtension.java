package org.cleanstack.ci.deploy;

import org.cleanstack.ci.spi.Extension;

public class DeployExtension extends Extension {

	public String getId() {
		return "deploy";
	}

	public String init() {
		return "hello";
	}
}