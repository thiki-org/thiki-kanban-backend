package org.thiki.kanban.user.profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.common.FileUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by xubt on 26/09/2016.
 */
public class AvatarStorageTest {
   private String location="uploadFiles";

    @Before
    public void setUp() throws IOException {
        FileUtil.deleteDirectory(new File(location));
    }
    @Scenario("当用户上传头像时,如果目录不存在,则创建相关目录")
    @Test
    public void createDirectoryIfDirectoryIsNotExists() throws IOException {
        File unExistsDirectory = new File("uploadFiles/avatars");
        assertFalse(unExistsDirectory.exists());

        File avatar = new File("src/test/resources/thiki-upload-test-file.jpg");
        AvatarStorage avatarStorage = new AvatarStorage();
        String userName = "someone";
        avatarStorage.store(userName, avatar);

        File directory = new File(AvatarStorage.location);
        assertTrue(directory.exists());
    }

    @After
    public void clearDirectory() throws IOException {
        FileUtil.deleteDirectory(new File(location));
    }
}
