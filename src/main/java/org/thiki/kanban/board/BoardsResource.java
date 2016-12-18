package org.thiki.kanban.board;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.worktile.WorktileController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class BoardsResource extends RestResource {
    @Resource
    private TLink tlink;

    @Resource
    private BoardResource boardResourceService;

    public Object toResource(List<Board> boards, String userName) throws Exception {
        this.domainObject = boards;

        List<Object> boardResources = new ArrayList<>();
        for (Board board : boards) {
            Object boardResource = boardResourceService.toResource(board, userName);
            boardResources.add(boardResource);
        }

        this.buildDataObject("boards", boardResources);
        Link selfLink = linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withSelfRel();

        this.add(tlink.from(selfLink));

        Link worktileTasksLinks = linkTo(methodOn(WorktileController.class).importTasks(userName, null)).withRel("worktileTasks");
        this.add(tlink.from(worktileTasksLinks));
        return getResource();
    }
}
