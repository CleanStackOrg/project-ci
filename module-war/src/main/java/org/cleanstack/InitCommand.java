package org.cleanstack;

import org.cleanstack.common.Arguments;

public class InitCommand {

    private static EmbeddedServer server;

    public static void main(String[] args) throws Exception {
	String cmd = args[0];
	int port = new Arguments(args).getArgumentInteger("httpPort", 8080);

	if ("start".equals(cmd)) {
	    System.out.println("Starting Embedded server");
	    server = new EmbeddedServer(port);
	    server.start();
	} else if ("stop".equals(cmd)) {
	    System.out.println("Stopping Embedded server");
	    server.stop();
	} else if ("batch".equals(cmd)) {
	    System.out.println("starting Batch");
	    System.out.println(Manager.init());
	}
    }
}
