package org.cleanstack.ci.deploy.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Playbook {

	Map<String, Object> playbook;

	public Playbook(File file) throws JsonParseException, JsonMappingException,
			IOException {
		playbook = read(file);
	}

	private Map read(File file) throws IOException, JsonParseException,
			JsonMappingException {
		return (Map) new ObjectMapper(new YAMLFactory()).readValue(
				FileUtils.readFileToString(file), List.class).get(0);
	}

	public String[] hosts() {
		return ((String) playbook.get("hosts")).replaceAll(" ", "").split(",");
	}

	public Map<String, Object> vars() {
		return (Map<String, Object>) playbook.get("vars");
	}

	public List<Map> tasks() {
		return (List<Map>) playbook.get("tasks");
	}
}
