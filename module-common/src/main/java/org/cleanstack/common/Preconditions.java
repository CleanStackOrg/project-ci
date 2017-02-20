package org.cleanstack.common;

public class Preconditions {

    // GUAVA LIKE

    public static <T> T checkNotNull(T reference) {
	if (reference == null) {
	    throw new NullPointerException();
	}
	return reference;
    }

    // APACHE COMMONS LIKE

    public static boolean isBlank(String str) {
	if (str == null) {
	    return true;
	}
	if ("".equals(str.replaceAll(" ", ""))) {
	    return true;
	}
	return false;
    }

    public static boolean isEmpty(String str) {
	if (str == null) {
	    return true;
	}
	if ("".equals(str)) {
	    return true;
	}
	return false;
    }

    public static boolean isNumeric(String str) {
	if (str == null) {
	    return false;
	}
	if ("".equals(str)) {
	    return true;
	}
	return str.matches("(\\d+)?");
    }

}
