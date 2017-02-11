package org.thiki.kanban.page;

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
import static org.hamcrest.core.StringEndsWith.endsWith;


/**
 * Created by xubt on 02/11/17.
 */
@Domain(order = DomainOrder.PAGE, name = "文库")
@RunWith(SpringJUnit4ClassRunner.class)
public class PageControllerTest extends TestBase {

    @Scenario("创建文章>用户可以为当前看板创建文章")
    @Test
    public void create_shouldReturn201WhenCreatePageSuccessfully() throws Exception {
        given().body("{\"title\":\"page-title\",\"content\":\"page-content\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/pages")
                .then()
                .statusCode(201)
                .body("title", equalTo("page-title"))
                .body("content", equalTo("page-content"))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/pages/fooId"))
                .body("_links.board.href", endsWith("/boards/boardId-foo"))
                .body("_links.pages.href", endsWith("/boards/boardId-foo/pages"));
    }

    @Scenario("更新文章>用户可以为更新已经存在的文章")
    @Test
    public void modifyPage() throws Exception {
        dbPreparation.table("kb_page")
                .names("id,title,content,board_id,author")
                .values("fooId", "page-title", "page-content", "boardId-foo", userName).exec();

        given().body("{\"title\":\"page-title-new\",\"content\":\"page-content-new\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/pages/fooId")
                .then()
                .statusCode(200)
                .body("title", equalTo("page-title-new"))
                .body("content", equalTo("page-content-new"))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/pages/fooId"))
                .body("_links.board.href", endsWith("/boards/boardId-foo"))
                .body("_links.pages.href", endsWith("/boards/boardId-foo/pages"));
    }

    @Scenario("更新文章>当文章不存在时，告知客户端错误")
    @Test
    public void notAllowedIfPageWasNotFoundWhenModifyingPage() throws Exception {
        given().body("{\"title\":\"page-title-new\",\"content\":\"page-content-new\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/pages/fooId")
                .then()
                .statusCode(404)
                .body("code", equalTo(PageCodes.PAGE_IS_NOT_EXISTS.code()))
                .body("message", equalTo(PageCodes.PAGE_IS_NOT_EXISTS.message()));
    }

    @Scenario("删除文章>用户创建文章后，可以删除")
    @Test
    public void removePage() throws Exception {
        dbPreparation.table("kb_page")
                .names("id,title,content,board_id,author")
                .values("fooId", "page-title", "page-content", "boardId-foo", userName).exec();
        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/pages/fooId")
                .then()
                .statusCode(200);
    }

    @Scenario("删除文章>删除文章时，如果文章不存在，告知客户端错误")
    @Test
    public void notAllowedIfPageWasNotFoundWhenRemovingPage() throws Exception {
        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/pages/fooId")
                .then()
                .statusCode(404)
                .body("code", equalTo(PageCodes.PAGE_IS_NOT_EXISTS.code()))
                .body("message", equalTo(PageCodes.PAGE_IS_NOT_EXISTS.message()));
    }

    @Scenario("获取指定文章>用户创建文章后，可以从文库中获取指定文章")
    @Test
    public void loadPageById() throws Exception {
        dbPreparation.table("kb_page")
                .names("id,title,content,board_id,author")
                .values("fooId", "page-title", "page-content", "boardId-foo", userName).exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/pages/fooId")
                .then()
                .statusCode(200)
                .body("title", equalTo("page-title"))
                .body("content", equalTo("page-content"))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/pages/fooId"))
                .body("_links.board.href", endsWith("/boards/boardId-foo"))
                .body("_links.pages.href", endsWith("/boards/boardId-foo/pages"));
    }

    @Scenario("获取指定文章>如果文章不存在，告知客户端错误")
    @Test
    public void notAllowedIfPageWasNotFoundWhenLoadingPage() throws Exception {
        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/pages/fooId")
                .then()
                .statusCode(404)
                .body("code", equalTo(PageCodes.PAGE_IS_NOT_EXISTS.code()))
                .body("message", equalTo(PageCodes.PAGE_IS_NOT_EXISTS.message()));
    }

    @Scenario("根据看板获取文章>用户创建文章后，可以从文库中获取指定文章集")
    @Test
    public void loadPages() throws Exception {
        dbPreparation.table("kb_page")
                .names("id,title,content,board_id,author")
                .values("fooId", "page-title", "page-content", "boardId-foo", userName).exec();

        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/pages")
                .then()
                .statusCode(200)
                .body("pages[0].title", equalTo("page-title"))
                .body("pages[0].content", equalTo("page-content"))
                .body("pages[0].author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/pages"))
                .body("_links.board.href", endsWith("/boards/boardId-foo"));
    }
}
