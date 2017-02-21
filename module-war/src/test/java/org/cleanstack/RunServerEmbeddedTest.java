package org.cleanstack;

import static com.jayway.restassured.RestAssured.expect;
import static java.lang.String.valueOf;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class RunServerEmbeddedTest {

    @Test
    public void test() throws Exception {
	int PORT = 8081;
	InitCommand.main(new String[] { //
	        "start", //
	        "-httpPort", valueOf(PORT) });

	RestAssured.port = PORT;
	expect().statusCode(200) //
	        .content(containsString("Hello")) //
	        .when().get("/");
	InitCommand.main(new String[] { "stop" });
    }

}
