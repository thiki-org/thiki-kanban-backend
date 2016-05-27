package org.thiki.kanban.entrance;

import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntranceResource extends RestResource {
    public EntranceResource() {
        this.domainObject = new JSONObject() {{
            put("description", "Welcome!");
        }};

        Link selfLink = linkTo(EntranceController.class).withSelfRel();
        Link boardsLink = linkTo(methodOn(BoardsController.class).loadAll()).withRel("boards");

        this.add(selfLink);
        this.add(boardsLink);
    }
}
