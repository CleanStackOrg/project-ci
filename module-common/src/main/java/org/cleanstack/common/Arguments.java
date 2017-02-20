package org.cleanstack.common;

import static java.lang.String.format;
import static org.cleanstack.common.Preconditions.isEmpty;

public class Arguments {

    String[] args;

    public Arguments(String[] args) {
	this.args = args;
    }

    public int getArgumentInteger(String key) {
	return Integer.valueOf(getArgument(key, null));
    }

    public int getArgumentInteger(String key, int defaultValue) {
	return Integer.valueOf(getArgument(key, String.valueOf(defaultValue)));
    }

    public String getArgument(String key) {
	return String.valueOf(getArgument(key, null));
    }

    private String getArgument(String key, String defaultValue) {
	if (isEmpty(key)) {
	    throw new IllegalArgumentException( //
	            format("key %s not found", key));
	}

	String result = "";
	String search = "-" + key;
	for (int i = 0; i < args.length; i++) {
	    if (search.equals(args[i])) {
		result = args[i + 1];
	    }
	}

	if (isEmpty(result)) {
	    if (defaultValue == null) {
		throw new RuntimeException( //
		        format("value is empty for key %s and no default value", key));
	    } else {
		result = defaultValue;
	    }
	}
	return result;
    }
}
