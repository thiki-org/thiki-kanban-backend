package org.thiki.kanban.user;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.aspect.ValidateParams;
import org.thiki.kanban.foundation.exception.InvalidParamsException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UsersService {

    @Resource
    private UsersPersistence usersPersistence;

    public UserProfile create(String authorUserId, final UserProfile userProfile) {
        UserProfile foundUserProfile = usersPersistence.findByEmail(userProfile.getEmail());
        if (foundUserProfile != null) {
            throw new InvalidParamsException("email[" + userProfile.getEmail() + "] is already exists.");
        }
        userProfile.setId(authorUserId);
        usersPersistence.create(userProfile);
        return usersPersistence.findById(userProfile.getId());
    }

    public UserProfile findById(String id) {
        return usersPersistence.findById(id);
    }

    public List<UserProfile> loadAll() {
        return usersPersistence.loadAll();
    }

    public UserProfile update(UserProfile userProfile) {
        usersPersistence.update(userProfile);
        return usersPersistence.findById(userProfile.getId());
    }

    public Integer deleteById(String id) {
        UserProfile userProfileToDelete = usersPersistence.findById(id);
        if (userProfileToDelete == null) {
            throw new ResourceNotFoundException("user[" + id + "] is not found.");
        }
        return usersPersistence.deleteById(id);
    }

    public UserProfile findByName(String userName) {
        return usersPersistence.findByName(userName);
    }

    @ValidateParams
    public User findByIdentity(@NotEmpty(message = UsersCodes.identityInRequired) String identity) {
        return usersPersistence.findByIdentity(identity);
    }
}
