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
}
