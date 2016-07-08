package org.thiki.kanban.user;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UsersService {

    @Resource
    private UsersPersistence usersPersistence;

    public UserProfile create(String reporterUserId, final UserProfile userProfile) {
        UserProfile foundUserProfile = usersPersistence.findByEmail(userProfile.getEmail());
        if (foundUserProfile != null) {
            throw new InvalidParameterException("email[" + userProfile.getEmail() + "] is already exists.");
        }
        userProfile.setId(reporterUserId);
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
}
