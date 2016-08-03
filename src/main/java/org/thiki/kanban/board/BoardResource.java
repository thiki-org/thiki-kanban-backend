package org.thiki.kanban.board;

import org.springframework.hateoas.Link;
import org.thiki.kanban.procedure.ProceduresController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 05/26/16.
 */
public class BoardResource extends RestResource {

    public BoardResource(Board board, String userName) {
        this.domainObject = board;
        if (board != null) {
            Link selfLink = linkTo(methodOn(BoardsController.class).findById(board.getId(), userName)).withSelfRel();
            this.add(selfLink);

            Link proceduresLink = linkTo(methodOn(ProceduresController.class).loadAll(board.getId())).withRel("procedures");
            this.add(proceduresLink);
        }
        this.add(linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withRel("all"));
    }

    public BoardResource(String userName) {
        this.add(linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withRel("all"));
    }
}
