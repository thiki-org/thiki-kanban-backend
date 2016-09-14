package org.thiki.kanban.user;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.aspect.ValidateParams;

import javax.annotation.Resource;

@Service
public class UsersService {

    @Resource
    private UsersPersistence usersPersistence;


    public User findById(String id) {
        return usersPersistence.findById(id);
    }

    public User findByName(String userName) {
        return usersPersistence.findByName(userName);
    }

    @ValidateParams
    public User findByIdentity(@NotEmpty(message = UsersCodes.identityInRequired) String identity) {
        return usersPersistence.findByIdentity(identity);
    }

    public User findByEmail(String email) {
        return usersPersistence.findByIdentity(email);
    }

    public User findByCredential(String identity, String md5Password) {
        return usersPersistence.findByCredential(identity, md5Password);
    }

    public boolean isNameExist(String userName) {
        return usersPersistence.isNameExist(userName);
    }

    public boolean isEmailExist(String email) {
        return usersPersistence.isEmailExist(email);
    }
}
