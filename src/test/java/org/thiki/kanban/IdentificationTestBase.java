package org.thiki.kanban;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Before;

/**
 * Created by xubt on 5/14/16.
 */
public class IdentificationTestBase extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        RequestSpecification requestSpecification = new RequestSpecBuilder().addHeader("identification", "yes").build();
        RestAssured.requestSpecification = requestSpecification;
    }
}
