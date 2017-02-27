package org.thiki.kanban.user;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xutao on 09/26/16.
 */
@RestController
public class UsersController {
    @Resource
    private UsersService usersService;
    @Resource
    private AvatarResource avatarResource;
    @Resource
    private ProfileResource profileResource;

    @RequestMapping(value = "/users/{userName}/avatar", method = RequestMethod.POST)
    public HttpEntity uploadAvatar(@PathVariable("userName") String userName, @RequestParam(value = "avatar", required = false) Object avatar) throws Exception {
        usersService.uploadAvatar(userName, (MultipartFile) avatar);
        return Response.build(avatarResource.toResource(userName));
    }

    @RequestMapping(value = "/users/{user}/avatar", method = RequestMethod.GET)
    public void loadAvatar(@PathVariable("user") String userName, HttpServletResponse response) throws IOException {
        InputStream avatar = usersService.loadAvatar(userName);
        org.apache.commons.io.IOUtils.copy(avatar, response.getOutputStream());
        response.setContentType("image/*");
        response.flushBuffer();
    }

    @RequestMapping(value = "/users/{user}/profile", method = RequestMethod.GET)
    public HttpEntity loadProfile(@PathVariable("user") String userName) throws Exception {
        Profile profile = usersService.loadProfileByUserName(userName);
        return Response.build(profileResource.toResource(profile, userName));
    }

    @RequestMapping(value = "/users/{userName}/profile", method = RequestMethod.PUT)
    public HttpEntity updateProfile(@RequestBody Profile userProfile, @PathVariable("userName") String userName) throws Exception {
        Profile profile = usersService.updateProfile(userProfile, userName);
        return Response.build(profileResource.toResource(profile, userName));
    }
}
