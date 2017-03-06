package org.thiki.kanban.risk;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.foundation.common.date.DateService;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;


/**
 * Created by cain on 2017/2/26.
 */
@Domain(order = DomainOrder.RISK, name = "风险")
@RunWith(SpringJUnit4ClassRunner.class)
public class RiskControllerTest extends TestBase{

    @Before
    public void setUp() throws Exception {
        super.setUp();
        //这一快的作用是啥？
        jdbcTemplate.execute("insert into kb_block (id, card_id, block_reson, start_time, end_time) " +
                "VALUES('block_id','card_id','block_reson','start_time','end_time')");

    }
//这个注解是怎么使用的，有什么作用；
    @Scenario("允许为张卡片创建延迟状态")
    @Test
    public void create_shouldReturn201WhenCreateDelaySuccessfully(){
        //参数怎么写

        int delayDays = 1;
        String currentTime = DateService.instance().getNow_EN();
        String startTime = DateService.instance().addDay(currentTime,0);
//        String endTime = DateService.instance().addDay(currentTime, delayDays);

        Map map = new HashMap();
        map.put("block_reson","risk reson");
        map.put("start_time",startTime);//开始阻塞的时间
//        map.put("end_time",endTime);  //为空的情况下代表此时还在阻塞



        given().body(map)
                .header("userName",userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId/risk")
                .then()
                .statusCode(201)
                .body("author",equalTo(userName))
                .body("delay_reson",equalTo("risk reson"))
                .body("_links.self.link",endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId/risk-fooId"))
                .body("_links.card.link",endsWith("/boards/boardId-foo/stages/stages-fooId/cards/card-fooId"));

    }


}
