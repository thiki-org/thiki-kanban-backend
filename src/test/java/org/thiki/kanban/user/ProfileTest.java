package org.thiki.kanban.user;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by xubt on 26/09/2016.
 */
@Domain(order = DomainOrder.USER, name = "用户")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProfileTest extends TestBase {

    @Scenario("个人资料>用户登录后,可以获取个人资料")
    @Test
    public void loadProfile() throws IOException {
        dbPreparation.table("kb_user_registration")
                .names("id,email,name,password")
                .values("fooUserId", "someone@gmail.com", "someone", "password").exec();

        dbPreparation.table("kb_user_profile")
                .names("id,user_name,nick_name")
                .values("foo-profile-id", "someone", "tao").exec();

        given().header("userName", "someone")
                .get("/users/someone/profile")
                .then()
                .statusCode(200)
                .body("userName", equalTo("someone"))
                .body("nickName", equalTo("tao"))
                .body("email", equalTo("someone@gmail.com"))
                .body("_links.avatar.href", equalTo("http://localhost:8007/users/someone/avatar"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/someone/profile"));
    }

    @Scenario("个人资料>用户登录后,可以获取个人资料")
    @Test
    public void initProfileIfProfileIsNotExist() throws IOException {
        dbPreparation.table("kb_user_registration")
                .names("id,email,name,password")
                .values("fooUserId", "someone@gmail.com", "someone", "password").exec();
        given().header("userName", "someone")
                .get("/users/someone/profile")
                .then()
                .statusCode(200)
                .body("userName", equalTo("someone"))
                .body("email", equalTo("someone@gmail.com"))
                .body("_links.avatar.href", equalTo("http://localhost:8007/users/someone/avatar"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/someone/profile"));
    }

    @Scenario("个人资料>用户可以更新个人资料")
    @Test
    public void updateProfile() throws IOException {
        dbPreparation.table("kb_user_registration")
                .names("id,email,name,password")
                .values("fooUserId", "someone@gmail.com", "someone", "password").exec();

        dbPreparation.table("kb_user_profile")
                .names("id,user_name,nick_name")
                .values("foo-profile-id", "someone", "tao").exec();

        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .body("{\"nickName\":\"nick-name\"}")
                .put("/users/someone/profile")
                .then()
                .statusCode(200)
                .body("nickName", equalTo("nick-name"))
                .body("email", equalTo("someone@gmail.com"))
                .body("_links.avatar.href", equalTo("http://localhost:8007/users/someone/avatar"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/someone/profile"));
    }
}
