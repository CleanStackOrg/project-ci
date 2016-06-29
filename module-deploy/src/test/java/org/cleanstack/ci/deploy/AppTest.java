package org.cleanstack.ci.deploy;

import org.cleanstack.ci.deploy.App;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class AppTest {

	@Test
	@Ignore
	public void local() throws Exception {
		String[] args = { //
				//
				"-i",
				"src/test/resources_test/hosts/hosts.ini", //
				"src/test/resources_test/playbooks/site.yml" //
		};
		App.main(args);
	}
	
	@Test
	@Ignore
	public void test_helloworld() throws Exception {
		String[] args = { //
				//
				"-i",
				"src/test/resources_test/hosts/hosts.ini", //
				"src/test/resources_test/playbooks/helloworld-site.yml" //
		};
		App.main(args);
	}
}
