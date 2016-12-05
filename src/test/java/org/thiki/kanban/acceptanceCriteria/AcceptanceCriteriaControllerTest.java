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
import static org.hamcrest.core.StringEndsWith.endsWith;


/**
 * Created by xubt on 10/17/16.
 */
@Domain(order = DomainOrder.ACCEPT_CRITERIA, name = "验收标准")
@RunWith(SpringJUnit4ClassRunner.class)
public class AcceptanceCriteriaControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,author,procedure_id) VALUES ('card-fooId','this is the first card.','someone','procedure-id-foo')");
    }

    @Scenario("创建验收标准>用户创建完卡片后,可以创建为其创建相应的验收标准")
    @Test
    public void create_shouldReturn201WhenCreateACSuccessfully() throws Exception {
        given().body("{\"summary\":\"AC-summary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias")
                .then()
                .statusCode(201)
                .body("summary", equalTo("AC-summary"))
                .body("finished", equalTo(false))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"))
                .body("_links.acceptanceCriterias.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias"));
    }

    @Scenario("创建验收标准>如果用户在创建验收标准时,未提供概述,则不允许创建")
    @Test
    public void notAllowedIfSummaryIsEmpty() throws Exception {
        given().body("{\"summary\":\"\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(AcceptanceCriteriaCodes.summaryIsRequired));
    }

    @Scenario("获取指定卡片的验收标准>用户为卡片创建验收标准后,可以查看")
    @Test
    public void loadAcceptanceCriterias() throws Exception {
        dbPreparation.table("kb_acceptance_criterias")
                .names("id,summary,card_id,author")
                .values("fooId", "AC-summary", "card-fooId", "someone").exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias")
                .then()
                .statusCode(200)
                .body("acceptanceCriterias[0].summary", equalTo("AC-summary"))
                .body("acceptanceCriterias[0].finished", equalTo(false))
                .body("acceptanceCriterias[0].author", equalTo(userName))
                .body("acceptanceCriterias[0].sortNumber", equalTo(9999))
                .body("acceptanceCriterias[0]._links.self.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId"))
                .body("acceptanceCriterias[0]._links.acceptanceCriterias.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias"))
                .body("_links.self.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"))
                .body("_links.sortNumbers.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/sortNumbers"));
    }

    @Scenario("获取指定的验收标准>用户为卡片创建验收标准后,可以根据ID获取指定的验收标准")
    @Test
    public void loadACById() throws Exception {
        dbPreparation.table("kb_acceptance_criterias")
                .names("id,summary,card_id,author")
                .values("fooId", "AC-summary", "card-fooId", "someone").exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("AC-summary"))
                .body("finished", equalTo(false))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"))
                .body("_links.acceptanceCriterias.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias"));
    }

    @Scenario("更新指定的验收标准>用户为卡片创建验收标准后,可以更新指定的验收标准")
    @Test
    public void updateAC() throws Exception {
        dbPreparation.table("kb_acceptance_criterias")
                .names("id,summary,card_id,author")
                .values("fooId", "AC-summary", "card-fooId", "someone").exec();

        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"summary\":\"new-AC-summary\",\"finished\":\"true\"}")
                .when()
                .put("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("new-AC-summary"))
                .body("finished", equalTo(true))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"))
                .body("_links.acceptanceCriterias.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias"));
    }

    @Scenario("删除指定的验收标准>用户为卡片创建验收标准后,可以删除指定的验收标准")
    @Test
    public void deleteAC() throws Exception {
        dbPreparation.table("kb_acceptance_criterias")
                .names("id,summary,card_id,author")
                .values("fooId", "AC-summary", "card-fooId", "someone").exec();

        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/fooId")
                .then()
                .statusCode(200)
                .body("_links.acceptanceCriterias.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias"));
    }

    @Scenario("调整验收标准的顺序>用户创建完卡片后,可以调整验收标准的排列顺序")
    @Test
    public void resortAcceptanceCriterias() throws Exception {
        dbPreparation.table("kb_acceptance_criterias")
                .names("id,summary,card_id,author")
                .values("acceptanceCriteria-id1", "AC-summary-1", "card-fooId", "someone").exec();
        dbPreparation.table("kb_acceptance_criterias")
                .names("id,summary,card_id,author")
                .values("acceptanceCriteria-id2", "AC-summary-2", "card-fooId", "someone").exec();

        given().body("[{\"id\":\"acceptanceCriteria-id1\",\"sortNumber\":\"1\"},{\"id\":\"acceptanceCriteria-id2\",\"sortNumber\":\"2\"}]")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/sortNumbers")
                .then()
                .statusCode(200)
                .body("acceptanceCriterias[0].summary", equalTo("AC-summary-1"))
                .body("acceptanceCriterias[0].sortNumber", equalTo(1))
                .body("acceptanceCriterias[1].summary", equalTo("AC-summary-2"))
                .body("acceptanceCriterias[1].sortNumber", equalTo(2))
                .body("_links.self.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"))
                .body("_links.sortNumbers.href", endsWith("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/acceptanceCriterias/sortNumbers"));
    }
}
