package org.thiki.kanban.user;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 5/18/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UsersControllerTest extends TestBase {

    @Test
    public void create_shouldReturn201WhenCreateUserSuccessfully() {
        given().header("userId", "11222")
                .body("{\"email\":\"someone@email.com\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("email", equalTo("someone@email.com"))
                .body("creationTime", notNullValue())
                .body("_links.users.href", equalTo("http://localhost:8007/users"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/fooId"));
    }

    @Test
    public void findById_shouldReturnUserWhenUserIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_user (id,email) VALUES ('fooId','someone@email.com')");
        given().header("userId", "11222")
                .when()
                .get("/users/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("email", equalTo("someone@email.com"))
                .body("_links.users.href", equalTo("http://localhost:8007/users"))
                .body("_links.boards.href", equalTo("http://localhost:8007/users/fooId/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/fooId"));
    }


    @Test
    public void update_shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_user (id,email) VALUES ('fooId','someone@email.com')");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"email\":\"others@email.com\"}")
                .when()
                .put("/users/fooId")
                .then()
                .statusCode(200)
                .body("email", equalTo("others@email.com"))
                .body("_links.users.href", equalTo("http://localhost:8007/users"))
                .body("_links.boards.href", equalTo("http://localhost:8007/users/fooId/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/fooId"));
        assertEquals("others@email.com", jdbcTemplate.queryForObject("select email from kb_user where id='fooId'", String.class));
    }

    @Test
    public void delete_shouldDeleteSuccessfullyWhenTheUserIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_user (id,email) VALUES ('fooId','someone@email.com')");
        given().header("userId", "11222")
                .when()
                .delete("/users/fooId")
                .then()
                .statusCode(200)
                .body("_links.users.href", equalTo("http://localhost:8007/users"));
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_user WHERE  delete_status=1").size());
    }

    @Test
    public void delete_shouldThrowResourceNotFoundExceptionWhenUserToDeleteIsNotExist() throws Exception {
        given().header("userId", "11222")
                .when()
                .delete("/users/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("user[fooId] is not found."));
    }

    @Test
    public void loadAll_shouldReturnAllUsersSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_user (id,email) VALUES ('fooId','someone@email.com')");
        given().header("userId", "11222")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("[0].email", equalTo("someone@email.com"))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.users.href", equalTo("http://localhost:8007/users"))
                .body("[0]._links.boards.href", equalTo("http://localhost:8007/users/fooId/boards"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/users/fooId"));
    }
}
