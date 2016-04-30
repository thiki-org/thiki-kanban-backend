package org.thiki.kanban.entry;

import cn.xubitao.dolphin.foundation.assmbler.DolphinAssembler;
import cn.xubitao.dolphin.foundation.resource.RestResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntriesResourceAssembler extends DolphinAssembler {
    public EntriesResourceAssembler() {
        super(EntriesController.class, RestResource.class);
    }

    @Override
    public RestResource toRestResource(Object domain, Object... pathVariables) throws Exception {
//        this.pathVariables = pathVariables;
//        EntryService entries = (EntryService) domain;
//        EntriesResource entriesResource = new EntriesResource();
//
//        Link entriesLink = linkTo(methodOn(EntriesController.class).loadAll()).withRel("entries");
//        if (entries == null) {
//            return RestResource.link(entriesLink);
//        }
//        List<ResourceSupport> contractResources = buildResources(entries.getEntries(), new EntryResourceAssembler(), pathVariables);
//        entriesResource.setEntries(contractResources);
//        entriesResource.add(linkTo(methodOn(EntriesController.class).loadAll()).withSelfRel());
//        return entriesResource;
        //FIXME: to refactoring
        return null;
    }
}
