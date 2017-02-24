package org.cleanstack;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.restassured.RestAssured.expect;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.containsString;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.cleanstack.common.SocketUtils;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class RunServerDeployedTest {

    @Test
    public void test() throws Exception {
	int PORT = 8082;
	TomcatServer server;
	server = new TomcatServer(PORT, "/");
	server.start();
	await() //
	        .atMost(4, SECONDS) //
	        .pollDelay(1, SECONDS) //
	        .until(() -> SocketUtils //
	                .findAvailableTcpPort(PORT));

	RestAssured.port = PORT;
	expect() //
	        .statusCode(200) //
	        .content(containsString("Hello")) //
	        .when().get("/");
	server.stop();
    }

    public class TomcatServer implements Runnable {

	private Tomcat tomcat;
	private Thread serverThread;

	public TomcatServer(int port, String contextPath) throws ServletException {
	    tomcat = new Tomcat();
	    tomcat.setPort(port);
	    tomcat.setBaseDir("target/tomcat");
	    tomcat.addWebapp(contextPath, new File("src/main/webapp").getAbsolutePath());
	    serverThread = new Thread(this);
	}

	public void start() {
	    serverThread.start();
	}

	@Override
	public void run() {
	    try {
		tomcat.start();
	    } catch (LifecycleException e) {
		throw new RuntimeException(e);
	    }
	    tomcat.getServer().await();
	}

	public void stop() {
	    try {
		tomcat.stop();
		tomcat.destroy();
		deleteDirectory(new File("target/tomcat/"));
	    } catch (LifecycleException e) {
		throw new RuntimeException(e);
	    }
	}

	void deleteDirectory(File path) {
	    if (path == null)
		return;
	    if (path.exists()) {
		for (File f : path.listFiles()) {
		    if (f.isDirectory()) {
			deleteDirectory(f);
			f.delete();
		    } else {
			f.delete();
		    }
		}
		path.delete();
	    }
	}
    }

}
