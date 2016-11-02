package org.thiki.kanban.board;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.worktile.WorktileController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 05/26/16.
 */
public class BoardsResource extends RestResource {
    public BoardsResource(List<Board> boards, String userName) throws Exception {
        this.domainObject = boards;

        List<BoardResource> boardResources = new ArrayList<>();
        for (Board board : boards) {
            BoardResource cardResource = new BoardResource(board, userName);
            boardResources.add(cardResource);
        }

        this.buildDataObject("boards", boardResources);
        Link selfLink = linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withSelfRel();
        this.add(selfLink);

        Link worktileTasksLinks = linkTo(methodOn(WorktileController.class).importTasks(userName, null)).withRel("worktileTasks");
        this.add(worktileTasksLinks);
    }
}
