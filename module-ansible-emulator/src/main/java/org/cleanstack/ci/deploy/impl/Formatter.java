package org.cleanstack.ci.deploy.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("rawtypes")
public class Formatter {

	public static String section(String string) {
		int size = 72;
		int offset = size - string.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < offset; i++) {
			sb.append("*");
		}
		return string + " " + sb.toString();
	}

	static String list(List<Map> tasks) {
		StringBuffer sb = new StringBuffer();
		for (Map t : tasks) {
			sb.append(" [");
			sb.append(t.get("name"));
			sb.append("]");
		}
		return sb.toString();
	}
	
	static String item(Map<String, String> args) {
		StringBuffer sb = new StringBuffer();
		sb.append("(item=(");
		for (Entry e : args.entrySet()) {
			sb.append(e.getKey());
			sb.append("=('");
			sb.append(e.getValue());
			sb.append("'), ");
		}
		sb.append("))");
	return sb.toString().replace(", )", ")");
	}
}