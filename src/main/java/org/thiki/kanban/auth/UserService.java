package org.thiki.kanban.auth;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by winie on 6/7/2016.
 */
@Service
public class UserService {
    @Resource
    private UserPersistence userPersistence;

    private List<User> users;

    public List<User> getEntries() {
        return users;
    }

    public User findById(String id) {
        return userPersistence.findById(id);
    }
    public User findByIdandsalt(String id,String salt) {
        return userPersistence.findByIdandsalt(id,salt);
    }
}
