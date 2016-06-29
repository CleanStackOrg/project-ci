package org.cleanstack.ci.deploy.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Variables {

	private Map<String, Object> vars;

	public Variables(Map<String, Object>... varsList) {
		vars = new HashMap<>();
		for (Map<String, Object> map : varsList) {
			Map<String, Object> newmap = replaceAllI2(map);
			vars.putAll(newmap);
		}

	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (Entry<String, Object> e : vars.entrySet()) {
			sb.append(e.getKey());
			sb.append("=('");
			sb.append(e.getValue());
			sb.append("'), ");
		}
		sb.append(")");
		return sb.toString();
	}

	public boolean has(String tag) {
		return vars.containsKey(tag);
	}

	public <T> T get(String tag) {
		checkArgument(vars.containsKey(tag(tag)), "undefined var: " + tag(tag));
		return (T) replaceAllI2(vars.get(tag(tag)));
	}

	private static final String TAG_END = " }}";
	private static final String TAG_START = "\\{\\{ ";

	public <T> T replaceAllI2(T actual) {
		T futur = null;
		futur = actual;
		if (actual instanceof String && !((String) actual).isEmpty()) {
			if (isFullVar(actual)) {
				String tag = tag((String) actual);
				if (has(tag)) {
					Object res = get(tag);
					if(res instanceof String) {
						futur = (T) replaceAllIn((String) actual);
					}
				}
			} else {
				futur = (T) replaceAllIn((String) actual);
			}
		} else if (actual instanceof Map) {
			futur = (T) replaceAllIn((Map) actual);
		} else if (actual instanceof ArrayList) {
			futur = (T) replaceAllIn((ArrayList) actual);
		}
		return futur;
	}

	private String tag(String string) {
		return string.replaceAll(TAG_START, "").replaceAll(TAG_END, "");
	}

	private boolean isFullVar(Object actual) {
		String var = (String) actual;
		boolean isSuroundWithBrackets = var.startsWith("{{ ") && var.endsWith(TAG_END);
		boolean isASingleVar = var.startsWith("{{ ") && !var.substring(2).contains("{{");
		return isSuroundWithBrackets && isASingleVar;
	}

	private Map<String, Object> replaceAllIn(Map<String, Object> map) {
		Map<String, Object> newmap = new HashMap<>();
		for (Entry<String, Object> e : map.entrySet()) {
			String key = e.getKey();
			Object actual = e.getValue();
			Object futur = replaceAllI2(actual);
			newmap.put(key, futur);
		}
		return newmap;
	}

	private List<Map> replaceAllIn(List<Map> list) {
		List<Map> newlist = new ArrayList<>();
		for (Map i : list) {
			newlist.add(replaceAllI2(i));
		}
		return newlist;
	}

	private String replaceAllIn(String string) {
		try {
			String[] as = string.split(TAG_START);
			Set<String> tags = new HashSet<>();
			if (as.length > 0) {
				for (int i = 0; i < as.length; i++) {
					String a = as[i];
					if (a.contains(TAG_END)) {
						String[] bs = a.split(TAG_END);
						for (int y = 0; y < bs.length; y++) {
							String b = bs[y];
							String tag = b.replace(" ", "");
							tags.add(tag);
							y++;
						}
					}
				}
			}
			String res = string;
			for (String tag : tags) {
				if (has(tag)) {
					String toreplace = TAG_START + tag + TAG_END;
					String replacement = get(tag);
					res = res.replaceAll(toreplace, replacement);
				} else {
					// DO NOTHING
				}
			}
			return res;
		} catch (Exception e) {
			System.out.println("ERROR on replaceAllIn: " + string);
			throw e;
		}
	}

}
