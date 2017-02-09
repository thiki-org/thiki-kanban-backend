package org.thiki.kanban.sprint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 04/02/2017.
 */
@Service
public class SprintResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(SprintResource.class);
    @Resource
    private TLink tlink;

    public Object toResource(Sprint sprint, String boardId, String userName) throws Exception {
        logger.info("build sprint resource.board:{},userName:{}", boardId, userName);
        SprintResource sprintResource = new SprintResource();
        sprintResource.domainObject = sprint;
        if (sprint != null) {
            Link selfLink = linkTo(methodOn(SprintController.class).findById(sprint.getId(), boardId, userName)).withSelfRel();
            sprintResource.add(tlink.from(selfLink).build(userName));

            Link boardLink = linkTo(methodOn(BoardsController.class).findById(boardId, userName)).withRel("board");
            sprintResource.add(tlink.from(boardLink).build(userName));
        }
        logger.info("sprint resource building completed.board:{},userName:{}", boardId, userName);
        return sprintResource.getResource();
    }
}
