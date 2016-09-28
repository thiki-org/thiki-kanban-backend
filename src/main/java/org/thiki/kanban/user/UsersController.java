package org.thiki.kanban.user;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by xutao on 09/26/16.
 */
@RestController
public class UsersController {
    @Resource
    private UsersService usersService;

    @RequestMapping(value = "/users/{userName}/avatar", method = RequestMethod.POST)
    public HttpEntity uploadAvatar(@PathVariable("userName") String userName, @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {
        usersService.uploadAvatar(userName, avatar);
        return Response.build(new AvatarResource(userName));
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    public HttpEntity loadUserByName(@PathVariable("userName") String userName) {
        return null;
    }
}
