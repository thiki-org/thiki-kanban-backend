package org.thiki.kanban.verification;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by xubt on 03/06/11.
 */
@Domain(order = DomainOrder.VERIFICATION, name = "核验")
@RunWith(SpringJUnit4ClassRunner.class)
public class VerificationControllerTest extends TestBase {

    @Test
    public void should_return_verifications_after_creating() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,wip_limit) VALUES ('stage-fooId','this is the first stage.','someone','boardId-foo',1)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,deadline,author,stage_id) VALUES ('card-fooId','this is the card summary.','play badminton','2017-02-13',1,'stage-fooId')");
        jdbcTemplate.execute("INSERT INTO  kb_acceptance_criterias (id,summary,card_id,finished,author) VALUES ('acceptanceCriteria-fooId','AC-summary','card-fooId',1,'someone')");
        given().header("userName", "someone")
                .body("{\"isPassed\":\"1\",\"remark\":\"remark-content\",\"acceptanceCriteriaId\":\"acceptanceCriteria-fooId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId/verifications")
                .then()
                .statusCode(201)
                .body("verifications[0].id", equalTo("fooId"))
                .body("verifications[0].acceptanceCriteriaId", equalTo("acceptanceCriteria-fooId"))
                .body("verifications[0].isPassed", equalTo(1))
                .body("verifications[0].remark", equalTo("remark-content"))
                .body("verifications[0].author", equalTo("someone"))
                .body("verifications[0]._links.verifications.href", endsWith("boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId/verifications"))
                .body("_links.self.href", endsWith("boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId/verifications"))
                .body("_links.acceptanceCriteria.href", endsWith("boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId"));
    }

    @Test
    public void should_return_verifications_when_loading_by_acceptance_criterias() {
        jdbcTemplate.execute("INSERT INTO  kb_verification (id,is_passed,remark,acceptance_criteria_id,author) VALUES ('verification-fooId',1,'remark-content','acceptanceCriteria-fooId','someone')");
        given().header("userName", "someone")
                .when()
                .get("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId/verifications")
                .then()
                .statusCode(200)
                .body("verifications[0].id", equalTo("verification-fooId"))
                .body("verifications[0].acceptanceCriteriaId", equalTo("acceptanceCriteria-fooId"))
                .body("verifications[0].isPassed", equalTo(1))
                .body("verifications[0].remark", equalTo("remark-content"))
                .body("verifications[0].author", equalTo("someone"))
                .body("verifications[0]._links.verifications.href", endsWith("boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId/verifications"))
                .body("_links.self.href", endsWith("boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId/verifications"))
                .body("_links.acceptanceCriteria.href", endsWith("boards/boardId-foo/stages/stage-fooId/cards/card-fooId/acceptanceCriterias/acceptanceCriteria-fooId"));
    }
}
