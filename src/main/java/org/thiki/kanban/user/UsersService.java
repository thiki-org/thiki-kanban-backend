package org.thiki.kanban.user;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.foundation.aspect.ValidateParams;
import org.thiki.kanban.foundation.common.FileUtil;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.user.profile.AvatarStorage;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Service
public class UsersService {
    public static final int AVATAR_MAX_SIZE = 102400;
    public static Logger logger = LoggerFactory.getLogger(UsersService.class);
    private static String avatarFileTempPath = "files/avatars/temp/";
    @Resource
    private UsersPersistence usersPersistence;
    @Resource
    private AvatarStorage avatarStorage;

    public User findByName(String userName) {
        return usersPersistence.findByName(userName);
    }

    @ValidateParams
    public User findByIdentity(@NotEmpty(message = UsersCodes.identityIsRequired) String identity) {
        return usersPersistence.findByIdentity(identity);
    }

    @ValidateParams
    public User findByEmail(@NotEmpty(message = UsersCodes.emailIsRequired) String email) {
        return usersPersistence.findByIdentity(email);
    }

    @ValidateParams
    public User findByCredential(@NotEmpty(message = UsersCodes.identityIsRequired) String identity, @NotEmpty(message = UsersCodes.md5PasswordIsRequired) String md5Password) {
        return usersPersistence.findByCredential(identity, md5Password);
    }

    @ValidateParams
    public boolean isNameExist(@NotEmpty(message = UsersCodes.userNameIsRequired) String userName) {
        return usersPersistence.isNameExist(userName);
    }

    @ValidateParams
    public boolean isEmailExist(@NotEmpty(message = UsersCodes.emailIsRequired) String email) {
        return usersPersistence.isEmailExist(email);
    }

    @CacheEvict(value = "avatar", key = "contains(#userName)", allEntries = true)
    public String uploadAvatar(String userName, MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            throw new BusinessException(UsersCodes.AVATAR_IS_EMPTY);
        }
        if (multipartFile.getSize() > AVATAR_MAX_SIZE) {
            throw new BusinessException(UsersCodes.AVATAR_IS_OUT_OF_MAX_SIZE);
        }
        File avatar = FileUtil.convert(avatarFileTempPath, multipartFile);
        String avatarName = avatarStorage.store(userName, avatar);
        UserProfile userProfile = usersPersistence.findProfile(userName);
        if (userProfile == null) {
            usersPersistence.initProfile(new UserProfile(userName));
        }
        usersPersistence.updateAvatar(userName, avatarName);
        return avatarName;
    }

    public File loadAvatar(String userName) throws IOException {
        logger.info("load avatar by userName:{}", userName);
        UserProfile userProfile = loadProfileByUserName(userName);
        return avatarStorage.loadAvatarByName(userProfile.getAvatar());
    }

    @Cacheable(value = "profile", key = "#userName")
    public UserProfile loadProfileByUserName(String userName) {
        UserProfile userProfile = usersPersistence.findProfile(userName);
        if (userProfile == null) {
            usersPersistence.initProfile(new UserProfile(userName));
        }
        return usersPersistence.findProfile(userName);
    }

    @CacheEvict(value = "profile", key = "contains(#userName)", allEntries = true)
    public UserProfile updateProfile(UserProfile userProfile, String userName) {
        usersPersistence.updateProfile(userName, userProfile);
        return usersPersistence.findProfile(userName);
    }
}
