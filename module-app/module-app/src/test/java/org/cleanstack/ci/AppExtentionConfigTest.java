package org.cleanstack.ci;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.cleanstack.ci.spi.Extension;
import org.junit.Test;

public class AppExtentionConfigTest {

	@Test
	public void test_init() {
		AppExtensionConfig classTotest = new AppExtensionConfig();
		Map<String, Extension> commands = classTotest.extensions;
		assertThat(commands) //
				.isEmpty();
		//
		classTotest //
				.initCommands();
		//
		assertThat(commands) //
				.isNotEmpty() //
				.hasSize(4) //
				.containsKeys("build-api","build-webui","deploy-api", "deploy-webui");

	}

	@Test
	public void test_extension() {
		AppExtensionConfig app = new AppExtensionConfig();
		app.initCommands();
		//
		Extension classTotest = app.extensions.get("deploy-api");
		String res = classTotest.init();
		//
		assertThat(res) //
			.isEqualTo("deploy-api init");
	}

}
