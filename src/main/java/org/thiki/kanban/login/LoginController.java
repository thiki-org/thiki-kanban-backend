package org.thiki.kanban.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubt on 7/5/16.
 */
@RestController
public class LoginController {
    public static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    public LoginService loginService;

    @Resource
    private IdentificationResource identificationResource;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public HttpEntity login(@RequestParam(required = false) String identity, @RequestParam(required = false) String password) throws Exception {
        logger.info("Login request arrived controller,identity:" + identity);
        Identification identification = loginService.login(identity, password);
        return Response.build(identificationResource.toResource(identification));
    }
}
