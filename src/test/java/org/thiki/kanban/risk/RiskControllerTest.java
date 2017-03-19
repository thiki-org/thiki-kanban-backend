package org.thiki.kanban.risk;

import com.alibaba.fastjson.JSONObject;
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
 * Created by cain on 2017/2/26.
 */
@Domain(order = DomainOrder.RISK, name = "风险")
@RunWith(SpringJUnit4ClassRunner.class)
public class RiskControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
//        jdbcTemplate.execute("insert into kb_risk (id, card_id, risk_reason, type, author, is_resolved) " +
//                "VALUES('risk_id','card_id','risk_reason','0','user_name','false')");

    }

    @Scenario("允许为张卡片创建延迟状态")
    @Test
    public void create_shouldReturn201WhenCreateDelaySuccessfully() {


        JSONObject body = new JSONObject();
        body.put("riskReason", "risk-reason");
        body.put("type", 0);
        given().body(body)
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId/risks")
                .then()
                .statusCode(201)
                .body("author", equalTo(userName))
                .body("riskReason", equalTo("risk-reason")) //风险理由
                .body("type", equalTo(0))  //代表block类型的风险
                .body("resolved", equalTo(false))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId/risks/fooId"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId"));

    }

    @Scenario("创建风险的时候，未填风险的理由情况下不允许创建风险")
    @Test
    public void notAllowedIfRiskReasonIsEmpty() {
        JSONObject body = new JSONObject();
        body.put("riskReason", "");
        body.put("type", 0);

        given().body(body)
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId/risks")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(RiskCodes.RISK_REASON_IS_EMPTY_MESSAGE));

    }

    @Scenario("用户创建风险>用户为某卡片创建风险后可以查看")
    @Test
    public void loadCardRisks() {

        jdbcTemplate.execute("insert into kb_risk (id, card_id, risk_reason, type, author, is_resolved) " +
                "VALUES('risk_id','card_id','risk_reason','0','user_name','false')");

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/stages/stages-fooId/cards/card_id/risks")
                .then()
                .statusCode(200)
                .body("risks[0].author", equalTo("user_name"))
                .body("risks[0].riskReason", equalTo("risk_reason")) //风险理由
                .body("risks[0].type", equalTo(0))  //代表block类型的风险
                .body("risks[0].resolved", equalTo(false))
                .body("risks[0]._links.self.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card_id/risks/risk_id"))
                .body("risks[0]._links.card.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card_id"))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card_id/risks"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card_id"));

    }

    @Scenario("可以通过风险id查看风险")
    @Test
    public void findRiskByRiskId(){
        jdbcTemplate.execute("insert into kb_risk (id, card_id, risk_reason, type, author, is_resolved) " +
                "VALUES('fooId','card-fooId','risk-reason','0','someone','false')");
        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId/risks/fooId")
                .then()
                .statusCode(200)
                .body("author", equalTo(userName))
                .body("riskReason", equalTo("risk-reason")) //风险理由
                .body("type", equalTo(0))  //代表block类型的风险
                .body("resolved", equalTo(false))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId/risks/fooId"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId"));

    }


    @Scenario("移除风险")
    @Test
    public void removeRisk() {

        jdbcTemplate.execute("insert into kb_risk (id, card_id, risk_reason, type, author, is_resolved) " +
                "VALUES('foo_id_rm','card_id','risk_reason','0','user_name','false')");

        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/stages/stages-fooId/cards/card_id/risks/foo_id_rm")
                .then()
                .statusCode(200)
                .body("_links.risks.href", endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card_id/risks"));

    }


    @Scenario("编辑风险")
    @Test
    public void editRisk() {

    }


}
