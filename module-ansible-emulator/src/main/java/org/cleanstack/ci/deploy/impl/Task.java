package org.cleanstack.ci.deploy.impl;

import static org.cleanstack.ci.deploy.impl.Formatter.list;
import static org.cleanstack.ci.deploy.impl.Formatter.section;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import org.cleanstack.ci.deploy.impl.Executor.Execution;
import org.cleanstack.ci.deploy.tools.HTTPClient;
import org.cleanstack.ci.deploy.tools.SCPClient;
import org.cleanstack.ci.deploy.tools.SSHClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jcraft.jsch.JSchException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Task {

	private String host;
	private Variables vars;
	private Inventory inv;

	public Task(String host, Variables vars, Inventory inv) {
		this.host = host;
		this.vars = vars;
		this.inv = inv;
	}

	public void execute(List<Map> tasks) throws Exception {
		System.out.println("will do :" + list(tasks));
		System.out.println(" ");
		for (Map<String, Object> task : tasks) {
			System.out.println(section("TASK: [" + task.get("name") + "]"));
			Map<String, Object> futur = vars.replaceAllI2(task);
			if (futur.containsKey("command")) {
				command(futur);
			} else if (futur.containsKey("get_url")) {
				get_url(futur);
			} else if (futur.containsKey("synchronize")) {
				synchronize(futur);
			} else if (futur.containsKey("include")) {
				include(futur);
			} else {
				throw new IllegalArgumentException(
						"Ansible can't interpret this task: " + futur);
			}
			System.out.println(" ");
		}
	}

	private void include(Map task) throws Exception {
		String playbookName = (String) task.get("include");
		String pathname = vars.has("ansible_scripts") ? vars
				.get("ansible_scripts") + "/" + playbookName : playbookName;
		List<Map> tasks = (List) new ObjectMapper(new YAMLFactory()).readValue(
				FileUtils.readFileToString(new File(pathname)), List.class);
		execute(tasks);
	}

	private void synchronize(Map task) throws IOException, JSchException {
		Map task2 = (Map) task.get("synchronize");
		String src = (String) task2.get("src");
		String dest = (String) task2.get("dest");
		String delegate_to = (String) task2.get("delegate_to");
		final Map<String, String> auth = inv.host("delegate.host"
				.equals(delegate_to) ? host : delegate_to);
		new Executor(host, vars).execute(task, new Execution() {
			public void execute(Map<String, String> args) {
				try (SCPClient scp = new SCPClient(//
						auth.get("ansible_host"), //
						auth.get("ansible_user"), //
						auth.get("ansible_ssh_pass"), 22)) {
					scp.upload(new File(args.get("src")).getAbsolutePath(),
							args.get("dest"));
				}
			}
		}, "src", src, "dest", dest);
	}

	private void get_url(Map task) throws IOException {
		String get_url = (String) task.get("get_url");
		int iurl = get_url.indexOf("url=");
		int idest = get_url.indexOf("dest=");
		int length = get_url.length();
		String url = get_url.substring(iurl, idest).replaceAll("url=", "");
		String dest = get_url.substring(idest, length).replaceAll("dest=", "");
		new Executor(host, vars).execute(task, new Execution() {
			public void execute(Map<String, String> args) {
				new HTTPClient().downloadTo(args.get("url"), args.get("dest"));
			}
		}, "url", url, "dest", dest);
	}

	private void command(Map task) throws FileNotFoundException {
		String command = (String) task.get("command");
		final Map<String, String> auth = inv.host(host);
		new Executor(host, vars).execute(task, new Execution() {
			public void execute(Map<String, String> args) {
				try (SSHClient ssh = new SSHClient(//
						auth.get("ansible_host"), //
						auth.get("ansible_user"), //
						auth.get("ansible_ssh_pass"), 22)) {
					ssh.exec(args.get("command"));
				}
			}
		}, "command", command);
	}
}
