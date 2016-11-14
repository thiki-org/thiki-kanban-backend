package org.thiki.kanban.cardTags;

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
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * Created by xubt on 11/14/16.
 */
@Domain(order = DomainOrder.CARD_TAGS, name = "卡片标签")
@RunWith(SpringJUnit4ClassRunner.class)
public class CardTagsControllerTest extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Scenario("贴标签>用户创建卡片后,可以给卡片贴标签,以区分卡片的不同属性")
    @Test
    public void stickTags() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("foo-tagId1", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        dbPreparation.table("kb_cards_tags")
                .names("id,card_id,tag_id,author")
                .values("foo-cards-tags", "card-fooId", "tagId-foo", "someone").exec();

        given().body("[{\"tagId\":\"foo-tagId1\"}]")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/tags")
                .then()
                .statusCode(201)
                .body("cardTags[0].name", equalTo("tag-name"))
                .body("cardTags[0].color", equalTo("tag-color"))
                .body("cardTags[0]._links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/tags"))
                .body("cardTags[0]._links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/tags"))
                .body("_links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"));

        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_cards_tags WHERE card_id='card-fooId' AND delete_status=0").size());
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_cards_tags WHERE id='foo-cards-tags' AND delete_status=0").size());
    }

    @Scenario("获取卡片标签>用户给卡片贴完标签后,可以获取卡片所贴标签")
    @Test
    public void loadTags() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("tagId-foo", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        dbPreparation.table("kb_cards_tags")
                .names("id,card_id,tag_id,author")
                .values("fooId", "card-fooId", "tagId-foo", "someone").exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/tags")
                .then()
                .statusCode(200)
                .body("cardTags[0].name", equalTo("tag-name"))
                .body("cardTags[0].color", equalTo("tag-color"))
                .body("cardTags[0]._links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/tags"))
                .body("cardTags[0]._links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId/tags"))
                .body("_links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/procedures-fooId/cards/card-fooId"));
    }
}
