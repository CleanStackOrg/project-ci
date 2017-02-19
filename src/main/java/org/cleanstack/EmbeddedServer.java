package org.cleanstack;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class EmbeddedServer {
	private final static Logger log = Logger.getLogger(EmbeddedServer.class.getName());

	private Server server;

	public EmbeddedServer(int port) {
		server = new Server(port);
		server.setHandler(new ServerHandler());
	}

	public void start() {
		log.info("EmbeddedServer.start");
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		log.info("EmbeddedServer.stop");
		try {
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static class ServerHandler extends AbstractHandler {

		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			response.setContentType("text/html;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			baseRequest.setHandled(true);
			response.getWriter().println("<h1>Hello World 4</h1>");
		}
	}

}
