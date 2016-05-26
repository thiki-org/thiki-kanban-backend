package org.thiki.kanban.board;

import org.springframework.hateoas.Link;
import org.thiki.kanban.entry.EntriesController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 05/26/16.
 */
public class BoardResource extends RestResource {

    public BoardResource(Board board) {
        this.domainObject = board;
        if (board != null) {
            Link selfLink = linkTo(methodOn(BoardsController.class).findById(board.getId())).withSelfRel();
            this.add(selfLink);

            Link entriesLink = linkTo(methodOn(EntriesController.class).loadAll(board.getId())).withRel("entries");
            this.add(entriesLink);
        }
        this.add(linkTo(methodOn(BoardsController.class).loadAll()).withRel("all"));
    }

    public BoardResource() {
        this.add(linkTo(methodOn(BoardsController.class).loadAll()).withRel("all"));
    }
}
