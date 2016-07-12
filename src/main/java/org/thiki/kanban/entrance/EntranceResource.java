package org.thiki.kanban.entrance;

import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.login.LoginController;

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

        Link identificationLink = linkTo(methodOn(LoginController.class).identify("yourUserName")).withRel("identification");
        this.add(identificationLink);
        this.add(selfLink);
    }
}
