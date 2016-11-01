package org.thiki.kanban.comment;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.AuthenticationTestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by xubt on 11/1/16.
 */
@Domain(order = DomainOrder.COMMENT, name = "评论")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentAuthenticationTest extends AuthenticationTestBase {
    @Scenario("删除指定的评论>当用户创建删除评论时,只可以删除由自己撰写的评论")
    @Test
    public void deleteComment() throws Exception {
        dbPreparation.table("kb_comment")
                .names("id,summary,card_id,author")
                .values("fooId", "comment-summary", "card-fooId", "others").exec();

        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"summary\":\"new-comment-summary\",\"finished\":\"true\"}")
                .when()
                .delete("/procedures/procedures-fooId/cards/card-fooId/comments/fooId")
                .then()
                .statusCode(401)
                .body("code", equalTo(CommentCodes.AUTH_THE_COMMENT_YOU_WANT_TO_DELETE_IS_NOT_YOURS.code()))
                .body("message", equalTo(CommentCodes.AUTH_THE_COMMENT_YOU_WANT_TO_DELETE_IS_NOT_YOURS.message()));
    }

    @Scenario("获取指定卡片的评论>用户在获取卡片的评论时,只可以操作自己的卡片")
    @Test
    public void loadComments() throws Exception {
        dbPreparation.table("kb_comment")
                .names("id,summary,card_id,author")
                .values("fooId1", "comment-summary", "card-fooId", "someone").exec();

        dbPreparation.table("kb_comment")
                .names("id,summary,card_id,author")
                .values("fooId", "comment-summary", "card-fooId", "others").exec();
        given().header("userName", userName)
                .when()
                .get("/procedures/procedures-fooId/cards/card-fooId/comments")
                .then()
                .statusCode(200)

                .body("comments[0]._links.self.actions.delete", notNullValue())
                .body("comments[1]._links.self.actions.delete", nullValue());

    }
}
