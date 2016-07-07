package org.thiki.kanban.login;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubt on 7/5/16.
 */
@RestController
public class IdentificationController {
    @Resource
    public IdentificationService identificationService;

    @RequestMapping("/hello")
    public String hello1(String[] param1, String param2) {
        return "hello" + param1[0] + param1[1] + param2;
    }

    @RequestMapping(value = "/identification", method = RequestMethod.GET)
    public HttpEntity create(@RequestHeader String name) throws Exception {
        PublicKey publicPublicKey = identificationService.generatePubicKey(name);

        return Response.build(new IdentificationResource(publicPublicKey));
    }
}
