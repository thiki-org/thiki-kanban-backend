package org.thiki.kanban.tag;

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
 * Created by xubt on 11/7/16.
 */
@Domain(order = DomainOrder.TAG, name = "标签")
@RunWith(SpringJUnit4ClassRunner.class)
public class TagsControllerTest extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Scenario("创建标签>用户可以创建标签,以便可以给看板的卡片归纳属性")
    @Test
    public void createTag() throws Exception {
        given().body("{\"name\":\"tag-name\",\"color\":\"tag-color\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/tags")
                .then()
                .statusCode(201)
                .body("name", equalTo("tag-name"))
                .body("color", equalTo("tag-color"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/tags/fooId"))
                .body("_links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"));
    }

    @Scenario("获取标签>用户为卡片创建标签后,可以查看")
    @Test
    public void loadTags() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/tags")
                .then()
                .statusCode(200)
                .body("tags[0].name", equalTo("tag-name"))
                .body("tags[0].color", equalTo("tag-color"))
                .body("tags[0]._links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/tags/fooId"))
                .body("tags[0]._links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"))
                .body("_links.clone.href", equalTo("http://localhost:8007/boards/boardId-foo/tags/clone?sourceBoardId="))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"));
    }

    @Scenario("更新标签>用户创建标签后,可以更新该标签的相关属性")
    @Test
    public void updateTag() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        given().body("{\"name\":\"tag-name-new\",\"color\":\"color-new\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/tags/fooId")
                .then()
                .statusCode(200)
                .body("name", equalTo("tag-name-new"))
                .body("color", equalTo("color-new"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/tags/fooId"))
                .body("_links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"));
    }

    @Scenario("删除标签>针对不再使用的标签,用户可以删除")
    @Test
    public void deleteTag() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/tags/fooId")
                .then()
                .statusCode(200)
                .body("_links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"));
    }

    @Scenario("创建标签>当用户创建标签时,如果同一看板下,已经存在相同名称,则不允许创建")
    @Test
    public void notAllowedIfTagNameIsAlreadyExist() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId-other", "tag-name-new", "tag-color", "someone", "boardId-foo").exec();

        given().body("{\"name\":\"tag-name-new\",\"color\":\"color-new\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/tags")
                .then()
                .statusCode(400)
                .body("code", equalTo(TagsCodes.NAME_IS_ALREADY_EXIST.code()))
                .body("message", equalTo(TagsCodes.NAME_IS_ALREADY_EXIST.message()));
    }

    @Scenario("创建标签>当用户创建签时,如果同一看板下,已经存在相同颜色,则不允许创建")
    @Test
    public void notAllowedIfTagColorIsAlreadyExist() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId-other", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        given().body("{\"name\":\"tag-name-new\",\"color\":\"tag-color\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/tags")
                .then()
                .statusCode(400)
                .body("code", equalTo(TagsCodes.COLOR_IS_ALREADY_EXIST.code()))
                .body("message", equalTo(TagsCodes.COLOR_IS_ALREADY_EXIST.message()));
    }

    @Scenario("更新标签>当用户更新标签时,如果同一看板下,已经存在相同名称,则不允许创建")
    @Test
    public void notAllowedIfTagNameIsAlreadyExistWhenUpdatingTag() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId-other", "tag-name-new", "tag-color", "someone", "boardId-foo").exec();

        given().body("{\"name\":\"tag-name-new\",\"color\":\"color-new\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/tags/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(TagsCodes.NAME_IS_ALREADY_EXIST.code()))
                .body("message", equalTo(TagsCodes.NAME_IS_ALREADY_EXIST.message()));
    }

    @Scenario("更新标签>当用户更新标签时,如果同一看板下,已经存在相同颜色,则不允许创建")
    @Test
    public void notAllowedIfTagColorIsAlreadyExistWhenUpdatingTag() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId-other", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        given().body("{\"name\":\"tag-name\",\"color\":\"color-new\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/tags/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(TagsCodes.NAME_IS_ALREADY_EXIST.code()))
                .body("message", equalTo(TagsCodes.NAME_IS_ALREADY_EXIST.message()));
    }

    @Scenario("复制标签>用户可以从某一看板中复制标签")
    @Test
    public void copyTagsFromOtherBoard() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        given().header("userName", userName)
                .param("sourceBoardId", "otherBoardId")
                .post("/boards/boardId-foo/tags/clone")
                .then()
                .body("_links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"));

        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_tag WHERE board_id='boardId-foo' AND delete_status=0").size());
    }

    @Scenario("复制标签>用户从某一看板中复制标签时,如果标签已经存在,则跳过该标签继续复制")
    @Test
    public void skipExistingTagWhenCopyingFromOthers() throws Exception {
        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId1", "tag-name", "tag-color", "someone", "otherBoardId").exec();

        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId2", "tag-name2", "tag-color", "someone", "otherBoardId").exec();

        dbPreparation.table("kb_tag")
                .names("id,name,color,author,board_id")
                .values("fooId3", "tag-name", "tag-color", "someone", "boardId-foo").exec();

        given().header("userName", userName)
                .when()
                .param("sourceBoardId", "otherBoardId")
                .post("/boards/boardId-foo/tags/clone")
                .then()
                .body("_links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"));

        assertEquals(2, jdbcTemplate.queryForList("SELECT * FROM kb_tag WHERE board_id='boardId-foo' AND delete_status=0").size());
    }

    @Scenario("复制标签>若用未提供来源看板信息,则不允许复制并告知客户端错误")
    @Test
    public void notAllowedIfSourceBoardIdNotProvided() throws Exception {
        given().header("userName", userName)
                .when()
                .post("/boards/boardId-foo/tags/clone")
                .then()
                .statusCode(400)
                .body("message", equalTo("Required String parameter 'sourceBoardId' is not present"));
    }
}
