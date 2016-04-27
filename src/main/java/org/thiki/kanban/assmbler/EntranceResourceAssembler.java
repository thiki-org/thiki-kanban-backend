package org.thiki.kanban.assmbler;

import cn.xubitao.dolphin.foundation.assmbler.DolphinAssembler;
import cn.xubitao.dolphin.foundation.resource.RestResource;
import org.thiki.kanban.controller.EntranceController;
import org.thiki.kanban.controller.EntriesController;
import org.thiki.kanban.resource.EntranceResource;
import org.springframework.hateoas.Link;
import org.thiki.kanban.controller.EntriesController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntranceResourceAssembler extends DolphinAssembler {
    public static final int MISSED = 0;

    public EntranceResourceAssembler() {
        super(EntranceController.class, RestResource.class);
    }

    @Override
    public RestResource toRestResource(Object domain, Object... pathVariables) throws Exception {
        this.pathVariables = pathVariables;
        EntranceResource entranceResource = new EntranceResource();
        entranceResource.setDescription("Welcome!");
        Link selfLink = linkTo(EntranceController.class).withSelfRel();
        Link entriesLink = linkTo(EntriesController.class).withRel("entries");

        entranceResource.add(selfLink);
        entranceResource.add(entriesLink);
        return entranceResource;
    }
}
