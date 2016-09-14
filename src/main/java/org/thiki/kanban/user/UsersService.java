package org.thiki.kanban.user;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.aspect.ValidateParams;

import javax.annotation.Resource;

@Service
public class UsersService {

    @Resource
    private UsersPersistence usersPersistence;

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
}
