package org.cleanstack;

public class Runner {

	public static void main(String[] args) throws Exception {
		// if() {
		//
		// } else {
		// }
		new LoggerConfiguration();
		EmbeddedServer server = new EmbeddedServer(8080);
		server.start();
	}
}
