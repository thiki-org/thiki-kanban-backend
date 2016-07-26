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
        List<UserProfile> userProfiles = usersService.loadAll();
        return Response.build(new UsersResource(userProfiles));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id) {
        UserProfile userProfile = usersService.findById(id);
        return Response.build(new UserResource(userProfile));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody UserProfile userProfile, @PathVariable String id) {
        userProfile.setId(id);
        UserProfile updatedUserProfile = usersService.update(userProfile);
        return Response.build(new UserResource(updatedUserProfile));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id) {
        usersService.deleteById(id);
        return Response.build(new UserResource());
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody UserProfile userProfile, @RequestHeader String userId) {
        UserProfile savedUserProfile = usersService.create(userId, userProfile);
        return Response.post(new UserResource(savedUserProfile));
    }
}
