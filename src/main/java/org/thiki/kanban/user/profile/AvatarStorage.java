package org.thiki.kanban.user.profile;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static Logger logger = LoggerFactory.getLogger(AvatarStorage.class);

    public String store(String userName, File avatar) throws IOException {
        logger.info("store avatar,user:{}", userName);
        File avatarsDirectory = new File(AVATAR_FILES_LOCATION);
        if (!avatarsDirectory.exists()) {
            FileUtil.forceMakeDirectory(avatarsDirectory);
        }
        String avatarName = avatar.getName();
        String avatarType = avatarName.substring(avatarName.lastIndexOf(".") + 1);
        avatarType = "blob".equals(avatarType) ? "jpg" : avatarType;
        String userAvatarName = userName + "." + avatarType;
        Files.write(Files.toByteArray(avatar), new File(AVATAR_FILES_LOCATION + userAvatarName));
        return userAvatarName;
    }

    public File loadAvatarByName(String avatarName) throws IOException {
        logger.info("load avatar,avatarName:{}", avatarName);

        String avatarPath = AVATAR_FILES_LOCATION + avatarName;
        File avatar = new File(avatarPath);
        if (!avatar.exists()) {
            logger.info("no avatar was found,return default avatar.");
            return FileUtil.loadFile(DEFAULT_AVATAR_FILES_LOCATION);
        }
        return avatar;
    }
}
