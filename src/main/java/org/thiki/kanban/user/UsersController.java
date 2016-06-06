package org.thiki.kanban.user;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UsersController {
    @Resource
    private UsersService usersService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public HttpEntity<UsersResource> loadAll() {
        List<User> users = usersService.loadAll();
        return Response.build(new UsersResource(users));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id) {
        User user = usersService.findById(id);
        return Response.build(new UserResource(user));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        User updatedUser = usersService.update(user);
        return Response.build(new UserResource(updatedUser));

    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id) {
        usersService.deleteById(id);
        return Response.build(new UserResource());
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody User user, @RequestHeader String userId) {
        User savedUser = usersService.create(userId, user);
        return Response.post(new UserResource(savedUser));
    }
}
