package org.thiki.kanban.board;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.procedure.ProceduresController;
import org.thiki.kanban.teams.team.TeamsController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 05/26/16.
 */
public class BoardResource extends RestResource {

    public static final String URL = "/{userName}/boards/{boardId}";

    public BoardResource(Board board, String userName) throws Exception {
        this.domainObject = board;
        if (board != null) {
            Link selfLink = linkTo(methodOn(BoardsController.class).findById(board.getId(), userName)).withSelfRel();
            this.add(selfLink);

            Link proceduresLink = linkTo(methodOn(ProceduresController.class).loadAll(board.getId())).withRel("procedures");
            this.add(proceduresLink);
            if (board.getTeamId() != null) {
                Link teamLink = linkTo(methodOn(TeamsController.class).findById(board.getTeamId(), userName)).withRel("team");
                this.add(teamLink);
            }
        }
        this.add(linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withRel("all"));
    }

    public BoardResource(String userName) throws Exception {
        this.add(linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withRel("all"));
    }
}
