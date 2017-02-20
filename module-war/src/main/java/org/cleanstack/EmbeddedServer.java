package org.cleanstack;

import static org.cleanstack.common.Throwables.propagate;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class EmbeddedServer implements Runnable {

    private final static Logger log = Logger.getLogger(EmbeddedServer.class.getName());

    private Server server;
    private Thread serverThread;

    public EmbeddedServer(int port) {
	log.info("EmbeddedServer using port " + port);
	server = new Server(port);
	server.setHandler(new ServerHandler());
	serverThread = new Thread(this);
    }

    public void start() {
	serverThread.start();
    }

    @Override
    public void run() {
	log.info("EmbeddedServer starting");
	try {
	    server.start();
	    server.join();
	} catch (Exception e) {
	    throw propagate(e);
	}
    }

    public void stop() {
	log.info("EmbeddedServer stopping");
	try {
	    server.stop();
	} catch (Exception e) {
	    throw propagate(e);
	}
    }

    static class ServerHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {
	    response.setContentType("text/html;charset=utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);
	    baseRequest.setHandled(true);
	    response.getWriter().println(Manager.init());
	}
    }

}
