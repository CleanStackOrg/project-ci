package org.cleanstack;

import static com.jayway.restassured.RestAssured.expect;

import org.junit.ClassRule;
import org.junit.Test;

public class WebserverTest {

  @ClassRule
  public static ServerExternalResource server = new ServerExternalResource();

  @Test
  public void test_start_stop() throws Exception {
    // String[] array = new String[] { "-port", "1234" };
    expect().statusCode(200) //
        .when().get("/");

    expect().statusCode(404) //
        .when().get("/");

  }

}
