package org.thiki.kanban.user;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.foundation.aspect.ValidateParams;
import org.thiki.kanban.foundation.common.FileUtil;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.user.profile.AvatarStorage;
import org.thiki.kanban.user.profile.StorageProperties;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UsersService {

    public static final int AVATAR_MAX_SIZE = 102400;
    private final Path rootLocation;
    @Resource
    private UsersPersistence usersPersistence;
    @Resource
    private AvatarStorage avatarStorage;

    public UsersService() {
        this.rootLocation = Paths.get(new StorageProperties().getLocation());
    }

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

    public String uploadAvatar(String userName, MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            throw new BusinessException(UsersCodes.AVATAR_IS_EMPTY);
        }
        if (multipartFile.getSize() > AVATAR_MAX_SIZE) {
            throw new BusinessException(UsersCodes.AVATAR_IS_OUT_OF_MAX_SIZE);
        }
        File avatar = FileUtil.convert(multipartFile);
        String avatarName = avatarStorage.store(userName, avatar);
        UserProfile userProfile = usersPersistence.findProfile(userName);
        if (userProfile == null) {
            usersPersistence.initProfile(new UserProfile(userName));
        }
        usersPersistence.updateAvatar(userName, avatarName);

        return avatarName;
    }
}
