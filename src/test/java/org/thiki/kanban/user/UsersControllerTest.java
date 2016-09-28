package org.thiki.kanban.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import java.io.File;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by xubt on 26/09/2016.
 */


@Domain(order = DomainOrder.USER, name = "用户")
@RunWith(SpringJUnit4ClassRunner.class)
public class UsersControllerTest extends TestBase {

    @Scenario("上传头像>用户成功上传头像")
    @Test
    public void uploadAvatar() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        File avatar = new File("src/test/resources/avatars/thiki-upload-test-file.jpg");
        map.add("avatar", new File("src/test/resources/avatars/thiki-upload-test-file.jpg"));
        given().header("userName", "someone")
                .multiPart("avatar", avatar)
                .post("/users/someone/avatar");
    }
}
