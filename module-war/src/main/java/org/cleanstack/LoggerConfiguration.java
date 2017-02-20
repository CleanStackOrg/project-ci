package org.cleanstack;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class LoggerConfiguration {

    private static final LogManager logManager = LogManager.getLogManager();

    static {
	System.out.println("h�");
	try {
	    InputStream inputStream = ClassLoader.class.getResourceAsStream("/logger.properties");

	    logManager.readConfiguration(inputStream);

	} catch (IOException exception) {
	    exception.printStackTrace();
	}
    }
}