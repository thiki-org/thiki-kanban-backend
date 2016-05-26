package org.thiki.kanban.entrance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 5/18/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EntranceControllerTest extends TestBase {
    @Test
    public void enter_shouldReturnEntranceSuccessfully() throws Exception {
        given().when()
                .get("/entrance")
                .then()
                .statusCode(200)
                .body("description", equalTo("Welcome!"))
                .body("_links.self.href", equalTo("http://localhost:8007/entrance"))
                .body("_links.boards.href", equalTo("http://localhost:8007/boards"));
    }
}
