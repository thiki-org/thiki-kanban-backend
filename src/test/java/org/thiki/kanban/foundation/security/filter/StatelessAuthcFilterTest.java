package org.thiki.kanban.foundation.security.filter;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 7/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class StatelessAuthcFilterTest extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        RequestSpecification requestSpecification = new RequestSpecBuilder().addHeader("authentication", "yes").build();
        RestAssured.requestSpecification = requestSpecification;
    }

    @Scenario("当请求需要认证时,如果没有携带token,则告知客户端需要授权")
    @Test
    public void shouldReturn401WhenAuthIsRequired() throws Exception {
        given().when()
                .get("/resource")
                .then()
                .statusCode(401)
                .body("code", equalTo(401))
                .body("message", equalTo("Authentication is required."))
                .body("_links.registration.href", equalTo("/registration"));
    }
}
