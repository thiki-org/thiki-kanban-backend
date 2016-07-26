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
    public void create_shouldFailedWhenEmailIsAlreadyExists() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) VALUES ('fooId2','someone@email.com','someone','password')");
        given().header("userId", "11222")
                .body("{\"email\":\"someone@email.com\",\"name\":\"someone\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .body("message", equalTo("email[someone@email.com] is already exists."));
    }

    @Test
    public void findById_shouldReturnUserWhenUserIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) VALUES ('fooId','someone@email.com','someone','password')");
        given().header("userId", "11222")
                .when()
                .get("/users/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("email", equalTo("someone@email.com"))
                .body("name", equalTo("someone"))
                .body("_links.users.href", equalTo("http://localhost:8007/users"))
                .body("_links.boards.href", equalTo("http://localhost:8007/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/fooId"));
    }


    @Test
    public void update_shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) VALUES ('fooId','someone@email.com','someone','password')");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"email\":\"others@email.com\",\"name\":\"others\"}")
                .when()
                .put("/users/fooId")
                .then()
                .statusCode(200)
                .body("email", equalTo("others@email.com"))
                .body("name", equalTo("others"))
                .body("_links.users.href", equalTo("http://localhost:8007/users"))
                .body("_links.boards.href", equalTo("http://localhost:8007/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/fooId"));
        assertEquals("others@email.com", jdbcTemplate.queryForObject("select email from kb_user_registration where id='fooId'", String.class));
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
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) VALUES ('fooId','someone@email.com','someone','password')");
        given().header("userId", "11222")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("[0].email", equalTo("someone@email.com"))
                .body("[0].name", equalTo("someone"))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.users.href", equalTo("http://localhost:8007/users"))
                .body("[0]._links.boards.href", equalTo("http://localhost:8007/boards"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/users/fooId"));
    }
}
