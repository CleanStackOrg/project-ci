package org.cleanstack;

import org.cleanstack.common.Arguments;

public class InitCommand {

    private static EmbeddedServer server;

    public static void main(String[] args) throws Exception {
	String cmd = args[0];
	int port = new Arguments(args).getArgumentInteger("httpPort", 8080);

	if ("start".equals(cmd)) {
	    server = new EmbeddedServer(port);
	    server.start();
	} else if ("stop".equals(cmd)) {
	    server.stop();
	}
    }
}
