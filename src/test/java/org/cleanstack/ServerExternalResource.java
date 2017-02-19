package org.cleanstack;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.rules.ExternalResource;

public class ServerExternalResource extends ExternalResource {
	private static final int PORT = 8080;

	EmbeddedServer server;

	@Override
	protected void before() throws Throwable {
		if (isServerReachable()) {
			server = new EmbeddedServer(PORT);
			server.start();
		}
	}

	@Override
	protected void after() {
		server.stop();
	}

	private boolean isServerReachable() {
		try (ServerSocket socket = new ServerSocket(PORT);) {
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
