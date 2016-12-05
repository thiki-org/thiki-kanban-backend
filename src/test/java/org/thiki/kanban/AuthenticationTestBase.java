package org.thiki.kanban;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import org.junit.Before;
import org.thiki.kanban.foundation.security.Constants;

/**
 * Created by xubt on 10/24/16.
 */
public class AuthenticationTestBase extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        requestSpecification = new RequestSpecBuilder()
                .addHeader(Constants.HEADER_PARAMS_IDENTIFICATION, "no")
                .addHeader(Constants.HEADER_PARAMS_AUTHENTICATION, "yes")
                .setBasePath(contextPath)
                .setPort(port)
                .build();
        RestAssured.requestSpecification = requestSpecification;
    }
}
