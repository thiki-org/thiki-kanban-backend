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
    private String location = "files";
    private File filesDirectory;

    @Before
    public void setUp() throws IOException {
        filesDirectory = new File(location);
        FileUtil.deleteDirectory(filesDirectory);
    }

    @Scenario("当用户上传头像时,如果目录不存在,则创建相关目录")
    @Test
    public void createDirectoryIfItDoesNotExists() throws IOException {
        File unExistsDirectory = new File("files/avatars");
        assertFalse(unExistsDirectory.exists());

        File avatar = new File("src/test/resources/thiki-upload-test-file.jpg");
        AvatarStorage avatarStorage = new AvatarStorage();
        String userName = "someone";
        avatarStorage.store(userName, avatar);

        File avatarDirectory = new File(AvatarStorage.FILES_LOCATION);
        assertTrue(avatarDirectory.exists());
    }

    @After
    public void clearDirectory() throws IOException {
        FileUtil.deleteDirectory(filesDirectory);
    }
}
