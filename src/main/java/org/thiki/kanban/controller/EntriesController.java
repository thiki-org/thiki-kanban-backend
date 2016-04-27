package org.thiki.kanban.controller;

import cn.xubitao.dolphin.foundation.resource.RestResource;
import cn.xubitao.dolphin.foundation.response.Response;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.assmbler.entry.EntriesResourceAssembler;
import org.thiki.kanban.assmbler.entry.EntryResourceAssembler;
import org.thiki.kanban.domain.entry.Entries;
import org.thiki.kanban.domain.entry.Entry;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
@RequestMapping(value = "/entries")
public class EntriesController {
    @Resource
    private Entries entries;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> loadAll() throws Exception {
        Entries entryList = entries.loadAll();
        return Response.build(entryList, new EntriesResourceAssembler());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> findById(@PathVariable Integer id) throws Exception {
        Entry entry = entries.findById(id);
        return Response.build(entry, new EntryResourceAssembler());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<ResourceSupport> update(@RequestBody Entry entry, @PathVariable Integer id) throws Exception {
        entry.setId(id);
        Entry updatedEntry = entries.update(entry);
        return Response.build(updatedEntry, new EntryResourceAssembler());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable Integer id) throws Exception {
        entries.deleteById(id);
        Link link = linkTo(methodOn(EntriesController.class).loadAll()).withRel("entries");
        return Response.ok(RestResource.link(link));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Entry entry) throws Exception {
        Entry savedEntry = entries.create(entry);
        EntryResourceAssembler entryResourceAssembler = new EntryResourceAssembler();
        RestResource entryResource = entryResourceAssembler.toRestResource(savedEntry);
        return Response.created(entryResource);
    }
}
