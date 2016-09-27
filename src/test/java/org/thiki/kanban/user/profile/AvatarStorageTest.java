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
    private File avatar;
    private AvatarStorage avatarStorage;
    private String userName = "someone";

    @Before
    public void setUp() throws IOException {
        filesDirectory = new File(location);
        avatarStorage = new AvatarStorage();

        avatar = new File("src/test/resources/avatars/thiki-upload-test-file.jpg");
        FileUtil.deleteDirectory(filesDirectory);

        File unExistsDirectory = new File("files/avatars");
        assertFalse(unExistsDirectory.exists());
    }

    @Scenario("当用户上传头像时,如果目录不存在,则创建相关目录")
    @Test
    public void createDirectoryIfItDoesNotExists() throws IOException {
        avatarStorage.store(userName, avatar);

        File avatarDirectory = new File(AvatarStorage.AVATAR_FILES_LOCATION);
        assertTrue(avatarDirectory.exists());
    }

    @Scenario("当用户上传头像时,保存到目录时将文件名修改为当前用户名")
    @Test
    public void renamingAvatarName() throws IOException {
        String avatarName = avatarStorage.store(userName, avatar);
        File savedAvatar = new File(AvatarStorage.AVATAR_FILES_LOCATION + avatarName);
        assertTrue(savedAvatar.exists());
    }

    @After
    public void clearDirectory() throws IOException {
        FileUtil.deleteDirectory(filesDirectory);
    }
}
