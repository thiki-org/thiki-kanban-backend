package org.thiki.kanban.entrance;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.thiki.kanban.entry.EntriesController;

import cn.xubitao.dolphin.foundation.assmbler.DolphinAssembler;
import cn.xubitao.dolphin.foundation.resource.RestResource;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntranceResourceAssembler extends DolphinAssembler {
    public static final int MISSED = 0;

    public EntranceResourceAssembler() {
        super(null, null);
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
