package org.thiki.kanban.registration;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.entry.Entry;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.hsqldb.lib.tar.TarHeaderField.name;

/**
 * Created by joeaniu on 6/21/16.
 */
@RestController
public class RegistrationController {
    @Resource
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public HttpEntity registerNewUser(@RequestBody Map<String, String> registrationForm) {

        UserRegistration userRegistration = registrationService.registerNewUser(
                registrationForm.get("name"),
                registrationForm.get("mail"),
                registrationForm.get("mobile"),
                registrationForm.get("passwd"),
                registrationForm.get("captcha")
        );

        return null;
//        return Response.build(new EntriesResource(entryList, boardId));

    }

}
