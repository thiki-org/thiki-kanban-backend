package org.thiki.kanban.user;

import org.junit.After;
import org.junit.Ignore;
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
public class AvatarTest extends TestBase {

    @Scenario("上传头像>用户成功上传头像")
    @Test
    public void uploadAvatar() {
        dbPreparation.table("kb_user_registration")
                .names("id,email,name,password")
                .values("fooUserId", "someone@gmail.com", "someone", "password").exec();
        File avatar = new File("src/test/resources/avatars/thiki-upload-test-file.jpg");

        given().header("userName", "someone")
                .multiPart("avatar", avatar)
                .post("/users/someone/avatar")
                .then()
                .statusCode(200)
                .body("_links.self.href", equalTo("http://localhost:8007/users/someone/avatar"))
                .body("_links.profile.href", equalTo("http://localhost:8007/users/someone/profile"));

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

    @Scenario("上传头像>用户上传头像时,如果头像文件大小超过限制,则告知客户端相关错误")
    @Test
    public void nowAllowedIfAvatarWasTooBig() {
        File avatar = new File("src/test/resources/avatars/big-avatar.jpg");

        given().header("userName", "someone")
                .multiPart("avatar", avatar)
                .post("/users/someone/avatar")
                .then()
                .statusCode(400)
                .body("code", equalTo(UsersCodes.AVATAR_IS_OUT_OF_MAX_SIZE.code()))
                .body("message", equalTo(UsersCodes.AVATAR_IS_OUT_OF_MAX_SIZE.message()));
    }

    @Ignore
    @Scenario("获取头像>用户在获取头像时,如果此前头像已经上传,则获取时则返回此前上传的头像")
    @Test
    public void loadAvatar() throws IOException {
        dbPreparation.table("kb_user_registration")
                .names("id,email,name,password")
                .values("fooUserId", "someone@gmail.com", "someone", "password").exec();

        dbPreparation.table("kb_user_profile")
                .names("id,user_name")
                .values("foo-profile-id", "someone").exec();

        File avatar = new File("src/test/resources/avatars/new-avatar.jpg");

        given().header("userName", "someone")
                .multiPart("avatar", avatar)
                .post("/users/someone/avatar");
    }

    @After
    public void clearDirectory() throws IOException {
        super.resetDB();
        FileUtil.deleteDirectory(new File("files"));
    }
}
