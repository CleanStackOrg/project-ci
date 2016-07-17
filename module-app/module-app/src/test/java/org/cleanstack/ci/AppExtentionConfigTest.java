package org.cleanstack.ci;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.cleanstack.ci.spi.Extension;
import org.cleanstack.ci.spi.UIExtension;
import org.junit.Test;

public class AppExtentionConfigTest {

	@Test
	public void test_init_api() {
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
				.hasSize(1) //
				.containsKeys("build-api");
	}

	@Test
	public void test_extension() {
		AppExtensionConfig app = new AppExtensionConfig();
		app.initCommands();
		//
		Extension classTotest = app.extensions.get("build-api");
		String res = classTotest.init();
		//
		assertThat(res) //
			.isEqualTo("build-api init");
	}

}
