package org.thiki.kanban.system.user;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UsersService {

    @Resource
    private UsersPersistence usersPersistence;

    public User create(Integer reporterUserId, final User user) {
        user.setId(reporterUserId);
        usersPersistence.create(user);
        return usersPersistence.findById(user.getId());
    }

    public User findById(int id) {
        return usersPersistence.findById(id);
    }

    public List<User> loadAll() {
        return usersPersistence.loadAll();
    }

    public User update(User user) {
        usersPersistence.update(user);
        return usersPersistence.findById(user.getId());
    }

    public int deleteById(int id) {
        User userToDelete = usersPersistence.findById(id);
        if (userToDelete == null) {
            throw new ResourceNotFoundException("user[" + id + "] is not found.");
        }
        return usersPersistence.deleteById(id);
    }
}
