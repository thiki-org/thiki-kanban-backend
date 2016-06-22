package org.thiki.kanban.registration;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by joeaniu on 6/21/16.
 */
@RestController
public class RegistrationController {
    @Resource
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public HttpEntity registerNewUser(@RequestBody Map<String, String> registrationForm, @RequestHeader String token) {
        Map<String, Object> result = registrationService.registerNewUser(
                registrationForm.get("name"),
                registrationForm.get("email"),
                registrationForm.get("phone"),
                registrationForm.get("password"),
                registrationForm.get("captcha")
        );
        RegistrationResource res = new RegistrationResource(result);
        res.add(linkTo(methodOn(RegistrationController.class).registerNewUser(registrationForm, token)).withSelfRel());

        return Response.post(res);

    }

}
