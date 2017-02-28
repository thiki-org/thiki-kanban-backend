package org.thiki.kanban.board;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 5/18/16.
 */
@Domain(order = DomainOrder.BOARD, name = "看板")
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardControllerTest extends TestBase {

    @Scenario("当创建一个board时,如果参数合法,则创建成功并返回创建后的board")
    @Test
    public void shouldReturn201WhenCreateBoardSuccessfully() {
        given().header("userName", "someone")
                .body("{\"name\":\"board-name\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/projects/project-fooId/boards")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("author", equalTo("someone"))
                .body("creationTime", notNullValue())
                .body("_links.all.href", endsWith("/someone/projects/project-fooId/boards"))
                .body("_links.tags.href", endsWith("/boards/fooId/tags"))
                .body("_links.stages.href", endsWith("/boards/fooId/stages"))
                .body("_links.sprintView.href", endsWith("/boards/fooId/stages?viewType=sprintView"))
                .body("_links.fullView.href", endsWith("/boards/fooId/stages?viewType=fullView"))
                .body("_links.roadMapView.href", endsWith("/boards/fooId/stages?viewType=roadMapView"))
                .body("_links.archiveView.href", endsWith("/boards/fooId/stages?viewType=archiveView"))
                .body("_links.sprintViewSnapshot.href", endsWith("/projects/project-fooId/boards/fooId/snapshot?viewType=sprintView"))
                .body("_links.fullViewSnapshot.href", endsWith("/projects/project-fooId/boards/fooId/snapshot?viewType=fullView"))
                .body("_links.roadMapViewSnapshot.href", endsWith("/projects/project-fooId/boards/fooId/snapshot?viewType=roadMapView"))
                .body("_links.archiveViewSnapshot.href", endsWith("/projects/project-fooId/boards/fooId/snapshot?viewType=archiveView"))
                .body("_links.snapshot.href", endsWith("/projects/project-fooId/boards/fooId/snapshot"))
                .body("_links.sprints.href", endsWith("/projects/project-fooId/boards/fooId/sprints"))
                .body("_links.activeSprint.href", endsWith("/boards/fooId/sprints/activeSprint"))
                .body("_links.pages.href", endsWith("/projects/project-fooId/boards/fooId/pages"))
                .body("_links.self.href", endsWith("/someone/projects/project-fooId/boards/fooId"));
    }

    @Scenario("用户根据ID获取board时,如果该board存在,则返回其信息")
    @Test
    public void shouldReturnBoardWhenBoardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author) VALUES ('fooId','board-name','someone')");

        given().header("userName", "someone")
                .log().all()
                .when()
                .get("/someone/projects/project-fooId/boards/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("author", equalTo("someone"))
                .body("_links.all.href", endsWith("/someone/projects/project-fooId/boards"))
                .body("_links.stages.href", endsWith("/boards/fooId/stages"))
                .body("_links.self.href", endsWith("/someone/projects/project-fooId/boards/fooId"));
    }

    @Scenario("成功更新一个board信息")
    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author) VALUES ('fooId','board-name','someone')");
        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"new-name\",\"projectId\":\"project-fooId\"}")
                .when()
                .put("/someone/projects/project-fooId/boards/fooId")
                .then()
                .statusCode(200)
                .body("name", equalTo("new-name"))
                .body("author", equalTo("someone"))
                .body("_links.all.href", endsWith("/someone/projects/project-fooId/boards"))
                .body("_links.self.href", endsWith("/someone/projects/project-fooId/boards/fooId"))
                .body("_links.project.href", endsWith("/projects/project-fooId"))
                .body("_links.members.href", endsWith("/projects/project-fooId/members"));
        assertEquals("new-name", jdbcTemplate.queryForObject("select name from kb_board where id='fooId'", String.class));
        assertEquals("project-fooId", jdbcTemplate.queryForObject("select project_id from kb_board where id='fooId'", String.class));
    }

    @Scenario("当看板不存在时,则不允许更新")
    @Test
    public void shouldUpdateFailedWhenTheBoardIsNotExist() {
        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"new-name\"}")
                .when()
                .put("/someone/projects/project-fooId/boards/fooId")
                .then()
                .statusCode(404)
                .body("code", equalTo(BoardCodes.BOARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(BoardCodes.BOARD_IS_NOT_EXISTS.message()));
    }

    @Scenario("当更新一个board时,如果存在同名,则不允许更新,并告知客户端错误信息")
    @Test
    public void UpdateIsNotAllowedIfBoardWithSameNameIsAlreadyExists() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,project_id,author) VALUES ('fooId1','board-name','project-fooId','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,project_id,author) VALUES ('fooId2','board-name2','project-fooId','someone')");
        given().header("userName", "someone")
                .body("{\"name\":\"board-name2\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/someone/projects/project-fooId/boards/fooId1")
                .then()
                .statusCode(400)
                .body("code", equalTo(BoardCodes.BOARD_IS_ALREADY_EXISTS.code()))
                .body("message", equalTo(BoardCodes.BOARD_IS_ALREADY_EXISTS.message()));
    }

    @Scenario("当用户删除一个指定的board时,如果该board存在,则删除成功")
    @Test
    public void shouldDeleteSuccessfullyWhenTheBoardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author,owner) VALUES ('fooId','board-name','someone','someone')");
        given().header("userName", "someone")
                .when()
                .delete("/someone/projects/project-fooId/boards/fooId")
                .then()
                .statusCode(200)
                .body("_links.all.href", endsWith("/someone/projects/project-fooId/boards"));
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_board WHERE  delete_status=1").size());
    }

    @Scenario("当用户删除一个指定的board时,如果该board不存在,则返回客户端404错误")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenBoardToDeleteIsNotExist() throws Exception {
        given().header("userName", "someone")
                .when()
                .delete("/someone/projects/project-fooId/boards/fooId")
                .then()
                .statusCode(404)
                .body("code", equalTo(BoardCodes.BOARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(BoardCodes.BOARD_IS_NOT_EXISTS.message()));
    }

    @Scenario("当用户创建一个board时,如果存在同名,则不允许创建,并告知客户端错误信息")
    @Test
    public void NotAllowedIfBoardWithSameNameIsAlreadyExists() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,project_id,author) VALUES ('fooId','board-name','project-fooId','someone')");
        given().header("userName", "someone")
                .body("{\"name\":\"board-name\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/projects/project-fooId/boards")
                .then()
                .statusCode(400)
                .body("code", equalTo(BoardCodes.BOARD_IS_ALREADY_EXISTS.code()))
                .body("message", equalTo(BoardCodes.BOARD_IS_ALREADY_EXISTS.message()));
    }
}
