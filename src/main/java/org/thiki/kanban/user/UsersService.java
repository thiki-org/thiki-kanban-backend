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

    public User create(String reporterUserId, final User user) {
        User foundUser = usersPersistence.findByEmail(user.getEmail());
        if (foundUser != null) {
            throw new InvalidParameterException("email[" + user.getEmail() + "] is already exists.");
        }
        user.setId(reporterUserId);
        usersPersistence.create(user);
        return usersPersistence.findById(user.getId());
    }

    public User findById(String id) {
        return usersPersistence.findById(id);
    }

    public List<User> loadAll() {
        return usersPersistence.loadAll();
    }

    public User update(User user) {
        usersPersistence.update(user);
        return usersPersistence.findById(user.getId());
    }

    public Integer deleteById(String id) {
        User userToDelete = usersPersistence.findById(id);
        if (userToDelete == null) {
            throw new ResourceNotFoundException("user[" + id + "] is not found.");
        }
        return usersPersistence.deleteById(id);
    }
}
