package org.thiki.kanban.foundation.apiDocument;

import com.jayway.restassured.http.ContentType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.user.registration.RegistrationService;

import javax.annotation.Resource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 6/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class APIDocumentTest extends TestBase {
    @Resource
    private RegistrationService registrationService;

    @Resource
    private RSAService rsaService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        ReflectionTestUtils.setField(registrationService, "sequenceNumber", sequenceNumber);

    }

    @Scenario("创建一个新的卡片")
    @Test
    public void create_shouldReturn201WhenCreateCardSuccessfully() throws Exception {
        Assert.assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        given().body("{\"summary\":\"summary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/procedures/fooId/cards")
                .then()
                .statusCode(201)
                .body("summary", equalTo("summary"))
                .body("author", equalTo(userName))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/fooId/cards/fooId"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/fooId/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/procedures/fooId/cards/fooId/assignments"));
        Assert.assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }
}
