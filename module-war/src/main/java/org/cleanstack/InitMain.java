package org.cleanstack;

public class InitMain {

    public static void main(String[] args) throws Exception {
	new LoggerConfiguration();
	EmbeddedServer server = new EmbeddedServer(8080);
	server.start();
    }
}
