package org.thiki.kanban.user.profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.common.FileUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by xubt on 26/09/2016.
 */
public class AvatarStorageTest {
    private String location = "files";
    private File filesDirectory;
    private File avatar;
    private File newAvatar;
    private AvatarStorage avatarStorage;
    private String userName = "someone";

    @Before
    public void setUp() throws IOException {
        filesDirectory = new File(location);
        avatarStorage = new AvatarStorage();

        avatar = new File("src/test/resources/avatars/thiki-upload-test-file.jpg");
        newAvatar = new File("src/test/resources/avatars/new-avatar.jpg");
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

    @Scenario("当用户上传头像时,若此前已经存在头像,且类型相同,则将其覆盖")
    @Test
    public void overwriteAvatarIfItAlreadyExists() throws IOException {
        String avatarName = avatarStorage.store(userName, avatar);
        String newAvatarName = avatarStorage.store(userName, newAvatar);
        assertEquals(avatarName, newAvatarName);
        File savedAvatar = new File(AvatarStorage.AVATAR_FILES_LOCATION + avatarName);
        assertTrue(savedAvatar.exists());
    }

    @Scenario("当用户上传头像时,若此前已经存在头像,且类型不同,则不覆盖")
    @Test
    public void NotOverwriteAvatarIfTypeIsNotSame() throws IOException {
        String avatarName = avatarStorage.store(userName, avatar);
        File pngAvatar = new File("src/test/resources/avatars/new-avatar.png");

        String expectedName = "someone.png";
        String actualName = avatarStorage.store(userName, pngAvatar);
        assertEquals(expectedName, actualName);
        File savedAvatar = new File(AvatarStorage.AVATAR_FILES_LOCATION + avatarName);
        assertTrue(savedAvatar.exists());
    }

    @Scenario("用户上传头像后,可以根据用户名获取头像")
    @Test
    public void loadAvatarByUserName() throws IOException {
        String avatarName = avatarStorage.store(userName, avatar);
        File myAvatar = avatarStorage.loadAvatarByName(avatarName);
        File savedAvatar = new File(AvatarStorage.AVATAR_FILES_LOCATION + avatarName);
        assertTrue(FileUtil.isFilesEqual(avatar, myAvatar));
        assertTrue(savedAvatar.exists());
    }

    @Scenario("获取用户头像时,如果头像不存在,则返回默认头像")
    @Test
    public void loadDefaultAvatarIfNoAvatarWasFound() throws IOException {
        String defaultAvatarPath = "src/main/resources/avatar/default-avatar.png";
        File defaultAvatar = new File(defaultAvatarPath);
        String avatarName = "foo";

        File myAvatar = avatarStorage.loadAvatarByName(avatarName);

        assertTrue(FileUtil.isFilesEqual(defaultAvatar, myAvatar));
    }

    @After
    public void clearDirectory() throws IOException {
        FileUtil.deleteDirectory(filesDirectory);
    }
}
