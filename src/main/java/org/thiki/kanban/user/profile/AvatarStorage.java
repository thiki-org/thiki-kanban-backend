package org.thiki.kanban.user.profile;

import com.google.common.io.Files;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by xubt on 26/09/2016.
 */

@Service
public class AvatarStorage {
    public static final String AVATAR_FILES_LOCATION = "files/avatars/";
    public static final String DEFAULT_AVATAR_FILES_LOCATION = "avatar/default-avatar.png";

    public String store(String userName, File avatar) throws IOException {
        File avatarsDirectory = new File(AVATAR_FILES_LOCATION);
        if (!avatarsDirectory.exists()) {
            FileUtil.forceMakeDirectory(avatarsDirectory);
        }
        String avatarName = avatar.getName();
        String avatarType = avatarName.substring(avatarName.lastIndexOf(".") + 1);
        avatarType = avatarType.equals("blob") ? "jpg" : avatarType;
        String userAvatarName = userName + "." + avatarType;
        Files.write(Files.toByteArray(avatar), new File(AVATAR_FILES_LOCATION + userAvatarName));
        return userAvatarName;
    }

    public File loadAvatarByName(String avatarName) throws IOException {
        String avatarPath = AVATAR_FILES_LOCATION + avatarName;
        File avatar = new File(avatarPath);
        if (!avatar.exists()) {
            return FileUtil.loadFile(DEFAULT_AVATAR_FILES_LOCATION);
        }
        return avatar;
    }
}
