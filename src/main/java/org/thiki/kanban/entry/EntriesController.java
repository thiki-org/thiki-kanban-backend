package org.thiki.kanban.entry;

import cn.xubitao.dolphin.foundation.resource.RestResource;
import cn.xubitao.dolphin.foundation.response.Response;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
@RequestMapping(value = "")
public class EntriesController {
    @Resource
    private EntriesService entryService;

    @RequestMapping(value = "/entries", method = RequestMethod.GET)
    public HttpEntity<EntriesResource> loadAll() {
        List<Entry> entryList = entryService.loadAll();
        List<EntryResource> resources = new EntryResourceAssembler().toResources(entryList);
        EntriesResource entriesRes = new EntriesResource();
        entriesRes.setEntries(resources);
       
        return new ResponseEntity<EntriesResource>(
                entriesRes, 
                entryList == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(value = "/entries/{id}", method = RequestMethod.GET)
    public HttpEntity<EntryResource> findById(@PathVariable Integer id) {
        Entry entry = entryService.findById(id);
        return new ResponseEntity<EntryResource>(
                new EntryResourceAssembler().toResource(entry), 
                HttpStatus.OK);
    }

    @RequestMapping(value = "/entries/{id}", method = RequestMethod.PUT)
    public HttpEntity<EntryResource> update(@RequestBody Entry entry, @PathVariable Integer id) {
        entry.setId(id);
        Entry updatedEntry = entryService.update(entry);
        
        return new ResponseEntity<EntryResource>(
                new EntryResourceAssembler().toResource(updatedEntry), 
                HttpStatus.OK);
    }

    @RequestMapping(value = "/entries/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<EntryResource> deleteById(@PathVariable Integer id) {
        entryService.deleteById(id);
        return new ResponseEntity<EntryResource>(
                new EntryResourceAssembler().emptyResource(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "{userId}/entries", method = RequestMethod.POST)
    public ResponseEntity<EntryResource> create(@RequestBody Entry entry, @PathVariable Integer userId) {
        
        Entry savedEntry = entryService.create(userId, entry);
        
        ResponseEntity<EntryResource> responseEntity = new ResponseEntity<EntryResource>(
                new EntryResourceAssembler().toResource(savedEntry), 
                HttpStatus.CREATED);
        return responseEntity;
    }
}
