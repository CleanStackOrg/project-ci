package org.cleanstack.ci.deploy.impl;

import static org.cleanstack.ci.deploy.impl.Formatter.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Throwables;

@SuppressWarnings({ "rawtypes", "unchecked" })
class Executor {

	private Variables vars;
	private String host;

	Executor(String host, Variables vars) {
		this.vars = vars;
		this.host = host;
	}

	void execute(Map task, Execution exe, String... fields) {
		List<Map<String, String>> iterable = new ArrayList();
		if (task.containsKey("with_items")) {
			// WITH_ITEMS
			String with_items = (String) task.get("with_items");
			List<Map> items = vars.get(with_items);
			for (Map item : items) {
				// MAP MULTIPLES
				Variables vars2 = new Variables(item);
				Map<String, String> m = new HashMap<>();
				for (int i = 0; i < fields.length; i++) {
					String k = fields[i];
					i++;
					String v = fields[i];
					String v2 = (String) vars2.replaceAllI2(v);
					m.put(k, v2);
				}
				iterable.add(m);
			}
		} else {
			// SINGLE ITEM
			Map<String, String> m = new HashMap<>();
			for (int i = 0; i < fields.length; i++) {
				String k = fields[i];
				i++;
				String v = fields[i];
				m.put(k, v);
			}
			iterable.add(m);
		}
		// EXECUTE
		for (Map<String, String> args : iterable) {
			try {
				exe.execute(args);
				System.out.println("ok: [" + host + "] => " + item(args));
			} catch (Exception e) {
				System.out.println("error: [" + host + "] => " + item(args));
				throw Throwables.propagate(e);
			}
		}
	}

	public interface Execution {
		void execute(Map<String, String> args);
	}

}
