package org.thiki.kanban.entry;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntryResourceAssembler extends ResourceAssemblerSupport<Entry, EntryResource> {

    protected Object[] pathVariables;

    public EntryResourceAssembler() {
        super(EntriesController.class, EntryResource.class);
    }
    
    
    public EntryResourceAssembler(Object... pathVariables){
        this();
        this.pathVariables = pathVariables;
    }
    
    public EntryResource emptyResource(){
        return buildResource(null);
    }
    
    private EntryResource buildResource(Entry entry){
        EntryResource resource = new EntryResource(entry);
        resource.add(linkTo(methodOn(EntriesController.class).loadAll()).withRel("all"));
        return resource;
    }
    
    @Override
    public EntryResource toResource(Entry entry) {
        EntryResource entryResource = buildResource(entry);
        if (entry != null) {
            Link selfLink = linkTo(methodOn(EntriesController.class).findById(entry.getId())).withSelfRel();
            entryResource.add(selfLink);
            
            Link delLink = linkTo(methodOn(EntriesController.class).deleteById(entry.getId())).withRel("del");
            entryResource.add(delLink);
        }
        return entryResource;
    }
    
}
