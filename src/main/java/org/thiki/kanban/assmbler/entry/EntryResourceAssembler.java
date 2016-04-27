package org.thiki.kanban.assmbler.entry;

import cn.xubitao.dolphin.foundation.assmbler.DolphinAssembler;
import cn.xubitao.dolphin.foundation.resource.RestResource;
import org.springframework.hateoas.Link;
import org.thiki.kanban.controller.EntriesController;
import org.thiki.kanban.domain.entry.Entry;
import org.thiki.kanban.resource.entry.EntryResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntryResourceAssembler extends DolphinAssembler {

    public EntryResourceAssembler() {
        super(EntriesController.class, RestResource.class);
    }

    @Override
    public RestResource toRestResource(Object domain, Object... pathVariables) throws Exception {
        this.pathVariables = pathVariables;
        Entry entry = (Entry) domain;
        EntryResource entryResource = new EntryResource();
        Link entriesLink = linkTo(methodOn(EntriesController.class).loadAll()).withRel("entries");
        if (domain == null) {
            return RestResource.link(entriesLink);
        }
        Link selfLink = linkTo(methodOn(EntriesController.class).findById(entry.getId())).withSelfRel();

        entryResource.setTitle(entry.getTitle());

        entryResource.add(entriesLink);
        entryResource.add(selfLink);
        return entryResource;
    }
}
