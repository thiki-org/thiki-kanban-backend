package org.thiki.kanban.registration;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;
import org.thiki.kanban.user.User;

import javax.annotation.Resource;

/**
 * Created by joeaniu on 6/21/16.
 */
@RestController
public class RegistrationController {
    @Resource
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public HttpEntity registerNewUser(@RequestBody Registration registration) throws Exception {
        User registeredUser = registrationService.register(registration);
        return Response.post(new RegistrationResource(registeredUser));
    }
}
