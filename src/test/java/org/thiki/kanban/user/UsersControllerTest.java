package org.thiki.kanban.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.foundation.common.FileUtil;

import java.io.File;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 26/09/2016.
 */
@Domain(order = DomainOrder.USER, name = "用户")
@RunWith(SpringJUnit4ClassRunner.class)
public class UsersControllerTest extends TestBase {

    @Scenario("上传头像>用户成功上传头像")
    @Test
    public void uploadAvatar() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','someone@gmail.com','someone','password')");
        File avatar = new File("src/test/resources/avatars/thiki-upload-test-file.jpg");

        given().header("userName", "someone")
                .multiPart("avatar", avatar)
                .post("/users/someone/avatar")
                .then()
                .statusCode(200)
                .body("_links.self.href", equalTo("http://localhost:8007/users/someone/avatar"))
                .body("_links.user.href", equalTo("http://localhost:8007/users/someone"));

        assertEquals("someone.jpg", jdbcTemplate.queryForObject("select avatar from kb_user_profile where user_name='someone'", String.class));
    }

    @Scenario("上传头像>用户上传头像时,如果未传头像文件,则告知客户端相关错误")
    @Test
    public void nowAllowedIfAvatarWasNull() {
        given().header("userName", "someone")
                .post("/users/someone/avatar")
                .then()
                .statusCode(400)
                .body("code", equalTo(UsersCodes.AVATAR_IS_EMPTY.code()))
                .body("message", equalTo(UsersCodes.AVATAR_IS_EMPTY.message()));
    }


    @After
    public void clearDirectory() throws IOException {
        super.resetDB();
        FileUtil.deleteDirectory(new File("files"));
    }
}
