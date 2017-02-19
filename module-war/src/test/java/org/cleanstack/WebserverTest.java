package org.cleanstack;

import static com.jayway.restassured.RestAssured.expect;

import javax.servlet.ServletException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class WebserverTest {

  private static final int PORT = 8080;

  private static EmbeddedServer server;

  @BeforeClass
  public static void beforeClass() throws ServletException {
    server = new EmbeddedServer(PORT);
    server.start();
    RestAssured.port = PORT;
  }

  @AfterClass
  public static void afterClass() {
    server.stop();
  }

  @Test
  public void test_start_stop() throws Exception {
    // String[] array = new String[] { "-port", "1234" };
    expect().statusCode(200) //
        .when().get("/");
  }

}
