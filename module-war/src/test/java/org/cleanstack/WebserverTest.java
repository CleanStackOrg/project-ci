package org.cleanstack;

import static com.jayway.restassured.RestAssured.expect;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class WebserverTest {

  @Test
  public void test_embedded_server_start_stop() throws Exception {
    int PORT = 8081;

    EmbeddedServer server;
    server = new EmbeddedServer(PORT);
    server.start();

    RestAssured.port = PORT;
    expect().statusCode(200) //
        .when().get("/");
    server.stop();
  }

  @Test
  public void test_webapp_on_server_start_stop() throws Exception {
    int PORT = 8082;

    TomcatServer server;
    server = new TomcatServer(PORT, "/");
    server.start();

    RestAssured.port = PORT;
    expect().statusCode(200) //
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
