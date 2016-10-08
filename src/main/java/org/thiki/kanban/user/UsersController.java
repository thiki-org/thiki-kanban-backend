package org.thiki.kanban.user;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by xutao on 09/26/16.
 */
@RestController
public class UsersController {
    @Resource
    private UsersService usersService;

    @RequestMapping(value = "/users/{userName}/avatar", method = RequestMethod.POST)
    public HttpEntity uploadAvatar(@PathVariable("userName") String userName, @RequestParam(value = "avatar", required = false) Object avatar) throws IOException {
        usersService.uploadAvatar(userName, (MultipartFile) avatar);
        return Response.build(new AvatarResource(userName));
    }

    @RequestMapping(value = "/users/{userName}/avatar", method = RequestMethod.GET)
    public HttpEntity loadAvatar(@PathVariable("userName") String userName) throws IOException {
        File avatar = usersService.loadAvatar(userName);
        return Response.build(new AvatarResource(userName, avatar));
    }

    @RequestMapping(value = "/users/{userName}/profile", method = RequestMethod.GET)
    public HttpEntity loadProfile(@PathVariable("userName") String userName) throws IOException {
        UserProfile profile = usersService.loadProfileByUserName(userName);
        return Response.build(new ProfileResourceResource(profile));
    }

    @RequestMapping(value = "/users/{userName}/profile", method = RequestMethod.PUT)
    public HttpEntity updateProfile(@RequestBody UserProfile userProfile, @PathVariable("userName") String userName) throws IOException {
        UserProfile profile = usersService.updateProfile(userProfile, userName);
        return Response.build(new ProfileResourceResource(profile));
    }
}
