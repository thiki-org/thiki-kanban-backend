package org.thiki.kanban.entry;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.task.TasksController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class EntryResource extends RestResource {
    public EntryResource(Entry entry, String boardId) {
        this.domainObject = entry;
        if (entry != null) {
            Link selfLink = linkTo(methodOn(EntriesController.class).findById(entry.getId(), boardId)).withSelfRel();
            this.add(selfLink);

            Link tasksLink = linkTo(methodOn(TasksController.class).create(null, entry.getId(), null)).withRel("tasks");
            this.add(tasksLink);
        }
        this.add(linkTo(methodOn(EntriesController.class).loadAll(boardId)).withRel("all"));
    }

    public EntryResource(String boardId) {
        this.add(linkTo(methodOn(EntriesController.class).loadAll(boardId)).withRel("all"));
    }
}
