package org.thiki.kanban.entrance;

import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.publickey.PublicKeyController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntranceResource extends RestResource {
    public EntranceResource() throws Exception {
        this.domainObject = new JSONObject() {{
            put("description", "Welcome!");
        }};

        Link selfLink = linkTo(EntranceController.class).withSelfRel();

        Link publicKeyLink = linkTo(methodOn(PublicKeyController.class).identify()).withRel("publicKey");
        this.add(publicKeyLink);
        this.add(selfLink);
    }
}
