package org.cleanstack.ci.deploy;

import static com.google.common.base.Preconditions.checkArgument;
import static org.cleanstack.ci.deploy.impl.Formatter.section;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.cleanstack.ci.deploy.impl.Inventory;
import org.cleanstack.ci.deploy.impl.Playbook;
import org.cleanstack.ci.deploy.impl.Task;
import org.cleanstack.ci.deploy.impl.Variables;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class App {

	public static void main(String[] args) throws Exception {
		try {
			System.out.println(section("*"));
			System.out.println(section("Starting Ansible emulator"));
			System.out.println(section("*"));

			// ARGS
			checkArgument(null != args && args.length > 0,
					"usage {playbook.yml inventory.txt}");
			String arg1 = args[1];
			System.out.println("inventory: " + arg1);
			String arg2 = args[2];
			System.out.println("playbookName: " + arg2);

			// READ
			File file = new File(arg2);
			Playbook playbook = new Playbook(file);

			// LOAD
			for (String host : playbook.hosts()) {
				Inventory inventory = new Inventory(arg1);
				Map<String, Object> invVars = inventory.vars(host);
				Map<String, Object> invHostVars = inventory.hostVars(host);
				Map<String, Object> playVars = playbook.vars();
				Variables variables = new Variables(invVars, invHostVars,
						playVars);

				// EXECUTE
				System.out.println(" ");
				System.out.println(section("*"));
				System.out.println(section("PLAY [" + host + "]"));
				Task task = new Task(host, variables, inventory);
				List<Map> tasks = playbook.tasks();
				task.execute(tasks);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}