package org.thiki.kanban.entrance;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.password.PasswordController;
import org.thiki.kanban.publickey.PublicKeyController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class EntranceResource extends RestResource {
    @Resource
    private TLink tlink;

    @Cacheable(value = "entrance-desc", key = "#root.methodName")
    public Object toResource() throws Exception {
        EntranceResource entranceResource = new EntranceResource();
        entranceResource.domainObject = new JSONObject() {{
            put("description", "Welcome!");
        }};

        Link selfLink = linkTo(EntranceController.class).withSelfRel();
        Link publicKeyLink = linkTo(methodOn(PublicKeyController.class).identify()).withRel("publicKey");
        Link passwordRetrievalLink = linkTo(methodOn(PasswordController.class).passwordRetrievalApply(null)).withRel("passwordRetrievalApplication");

        entranceResource.add(tlink.from(selfLink).build());
        entranceResource.add(tlink.from(publicKeyLink).build());
        entranceResource.add(tlink.from(passwordRetrievalLink).build());
        return entranceResource.getResource();
    }
}
