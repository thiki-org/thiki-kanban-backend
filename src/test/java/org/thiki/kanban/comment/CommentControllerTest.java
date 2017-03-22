package org.thiki.kanban.comment;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaCodes;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;


/**
 * Created by xubt on 31/10/16.
 */
@Domain(order = DomainOrder.COMMENT, name = "评论")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('stage-fooId','this is the first stage.',1,'feeId')");

        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,author,stage_id) VALUES ('card-fooId','this is the first card.','someone','stage-fooId')");
    }

    @Scenario("撰写评论>用户创建完卡片后,可以创建为其撰写相应的评论")
    @Test
    public void create_shouldReturn201WhenCreateACSuccessfully() throws Exception {
        given().body("{\"summary\":\"comment-summary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments")
                .then()
                .statusCode(201)
                .body("summary", equalTo("comment-summary"))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments/fooId"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId"))
                .body("_links.comments.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments"));
    }

    @Scenario("创建评论>如果用户在创建评论时,未提供概述,则不允许创建")
    @Test
    public void notAllowedIfSummaryIsEmpty() throws Exception {
        given().body("{\"summary\":\"\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(AcceptanceCriteriaCodes.summaryIsRequired));
    }

    @Scenario("获取指定卡片的评论>用户为卡片创建评论后,可以查看")
    @Test
    public void loadComments() throws Exception {
        dbPreparation.table("kb_comment")
                .names("id,summary,card_id,author")
                .values("fooId", "comment-summary", "card-fooId", "someone").exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments")
                .then()
                .statusCode(200)
                .body("comments[0].summary", equalTo("comment-summary"))
                .body("comments[0].author", equalTo(userName))
                .body("comments[0]._links.self.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments/fooId"))
                .body("comments[0]._links.comments.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments"))
                .body("comments[0]._links.avatar.href", endsWith("/users/someone/avatar"))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId"));
    }

    @Scenario("获取指定的评论>用户为卡片创建评论后,可以根据ID获取指定的评论")
    @Test
    public void loadCommentById() throws Exception {
        dbPreparation.table("kb_comment")
                .names("id,summary,card_id,author")
                .values("fooId", "comment-summary", "card-fooId", "someone").exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("comment-summary"))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments/fooId"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId"))
                .body("_links.comments.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments"));
    }

    @Scenario("删除指定的评论>用户为卡片创建评论后,可以删除指定的评论")
    @Test
    public void deleteComment() throws Exception {
        dbPreparation.table("kb_comment")
                .names("id,summary,card_id,author")
                .values("fooId", "comment-summary", "card-fooId", "someone").exec();

        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"summary\":\"new-comment-summary\",\"finished\":\"true\"}")
                .when()
                .delete("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments/fooId")
                .then()
                .statusCode(200)
                .body("_links.comments.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/comments"));
    }
}
