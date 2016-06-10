package org.thiki.kanban.entry;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
public class EntriesController {
    @Resource
    private EntriesService entriesService;

    @RequestMapping(value = "/boards/{boardId}/entries", method = RequestMethod.GET)
    public HttpEntity loadAll(@PathVariable String boardId) {
        List<Entry> entryList = entriesService.loadByBoardId(boardId);
        return Response.build(new EntriesResource(entryList, boardId));

    }

    @RequestMapping(value = "/boards/{boardId}/entries/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @PathVariable String boardId) {
        Entry entry = entriesService.findById(id);
        return Response.build(new EntryResource(entry, boardId));

    }

    @RequestMapping(value = "/boards/{boardId}/entries/{id}", method = RequestMethod.PUT)
    public HttpEntity<EntryResource> update(@RequestBody Entry entry, @PathVariable String id, @PathVariable String boardId) {
        entry.setId(id);
        Entry updatedEntry = entriesService.update(entry);

        return Response.build(new EntryResource(updatedEntry, boardId));
    }

    @RequestMapping(value = "/boards/{boardId}/entries/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id, @PathVariable String boardId) {
        entriesService.deleteById(id);
        return Response.build(new EntryResource(boardId));

    }

    @RequestMapping(value = "/boards/{boardId}/entries", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Entry entry, @RequestHeader Integer userId, @PathVariable String boardId) {
        Entry savedEntry = entriesService.create(userId, entry);

        return Response.post(new EntryResource(savedEntry, boardId));
    }
}
