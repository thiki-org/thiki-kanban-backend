package org.thiki.kanban.login;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubt on 7/5/16.
 */
@RestController
public class LoginController {
    @Resource
    public LoginService loginService;

    @RequestMapping(value = "/identification", method = RequestMethod.GET)
    public HttpEntity identify(@RequestHeader String name) throws Exception {
        PublicKey publicPublicKey = loginService.generatePubicKey(name);

        return Response.build(new PublicKeyResource(publicPublicKey));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public HttpEntity login(@RequestParam String identity, @RequestParam String password) throws Exception {
        Identification identification = loginService.login(identity, password);

        return Response.build(new IdentificationResource(identification));
    }
}
