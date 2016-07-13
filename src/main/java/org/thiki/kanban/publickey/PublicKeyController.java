package org.thiki.kanban.publickey;

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
public class PublicKeyController {
    @Resource
    public PublicKeyService publicKeyService;

    @RequestMapping(value = "/public-key", method = RequestMethod.GET)
    public HttpEntity identify(@RequestHeader String name) throws Exception {
        PublicKey publicPublicKey = publicKeyService.authenticate(name);

        return Response.build(new PublicKeyResource(name, publicPublicKey));
    }
}
