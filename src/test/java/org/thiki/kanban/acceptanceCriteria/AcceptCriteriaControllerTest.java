package org.thiki.kanban.acceptanceCriteria;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * Created by xubt on 10/17/16.
 */
@Domain(order = DomainOrder.ACCEPT_CRITERIA, name = "验收标准")
@RunWith(SpringJUnit4ClassRunner.class)
public class AcceptCriteriaControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,author) VALUES ('card-fooId','this is the first card.','someone')");
    }

    @Scenario("创建验收标准>用户创建完卡片后,可以创建为其创建相应的验收标准")
    @Test
    public void create_shouldReturn201WhenCreateACSuccessfully() throws Exception {
        given().body("{\"summary\":\"AC-summary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/cards/card-fooId/acceptanceCriterias")
                .then()
                .statusCode(201)
                .body("summary", equalTo("AC-summary"))
                .body("isFinished", equalTo(0))
                .body("author", equalTo(userName))
                .body("_links.self.href", equalTo("http://localhost:8007/cards/card-fooId/acceptanceCriterias/fooId"))
                .body("_links.acceptanceCriterias.href", equalTo("http://localhost:8007/cards/card-fooId/acceptanceCriterias"));
    }
}
