package org.thiki.kanban;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import org.junit.Before;

/**
 * Created by xubt on 5/14/16.
 */
public class IdentificationTestBase extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        requestSpecification = new RequestSpecBuilder()
                .addHeader("identification", "yes")
                .setBasePath(contextPath)
                .setPort(port)
                .build();
        RestAssured.requestSpecification = requestSpecification;
    }
}
