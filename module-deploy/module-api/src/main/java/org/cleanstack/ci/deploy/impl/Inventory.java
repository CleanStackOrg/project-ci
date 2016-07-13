package org.cleanstack.ci.deploy.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.ImmutableMap;

public class Inventory {

	String filePath;

	public Inventory(String filePath) {
		this.filePath = filePath;
	}

	public Map<String, String> host(String host) throws FileNotFoundException {
		String u = null;
		String p = null;
		// SEARCH HOST NAME
		int hostLineNum = fileLineNum(new File(filePath), "[" + host + "]");
		String hostLine = fileLine(new File(filePath), hostLineNum + 1);
		String[] split = hostLine.split(" ");
		String h = split[0];
		for (String string : split) {
			if (string.contains("ansible_user="))
				u = string.replace("ansible_user=", "");
			if (string.contains("ansible_ssh_pass="))
				p = string.replace("ansible_ssh_pass=", "");
		}
		return ImmutableMap.of(//
				"ansible_host", h, //
				"ansible_user", u, //
				"ansible_ssh_pass", p);
	}

	public Map<String, Object> vars(String host) throws FileNotFoundException {
		Map<String, Object> vars = new HashMap<>();
		try (Scanner scanner = new Scanner(new File(filePath))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.contains("[[") && !line.isEmpty()) {
					
					String[] split = line.split("=");
					String key = split[0];
					String val = split.length > 1 ? split[1] : "";
					vars.put(key, val);
					
				} else {
					return vars;
				}
			}
		}
		return vars;
	}

	public Map<String, Object> hostVars(String host)
			throws FileNotFoundException {
		File file = new File(filePath);
		// SEARCH HOST NAME
		int hostLineNum = fileLineNum(file, "[" + host + ":vars]");
		// GET
		Map<String, Object> vars = new HashMap<>();
		try (Scanner scanner = new Scanner(file)) {
			int lineNum = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNum++;
				if (lineNum > hostLineNum) {
					if (!line.isEmpty()) {
						
						String[] split = line.split("=");
						String k = split[0];
						String v = split.length > 1 ? split[1] : "";
						vars.put(k, v);
					} else {
						break;
					}
				}
			}
		}
		return vars;
	}

	private String fileLine(File file, int hostLineNum)
			throws FileNotFoundException {
		try (Scanner scanner = new Scanner(file)) {
			int lineNum = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNum++;
				if (lineNum == hostLineNum) {
					return line;
				}
			}
			throw new IllegalArgumentException(
					"host content not found in inventory for " + file);
		}
	}

	private int fileLineNum(File file, String expression)
			throws FileNotFoundException {
		try (Scanner scanner = new Scanner(file)) {
			int lineNum = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNum++;
				if (line.contains(expression)) {
					return lineNum;
				}
			}
			throw new IllegalArgumentException("host '" + expression
					+ "' not found in inventory for " + file);
		}
	}
}
