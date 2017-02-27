package org.thiki.kanban.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.stage.StagesController;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 6/16/16.
 */
@Service
public class AssignmentResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(AssignmentResource.class);
    @Resource
    private TLink tlink;

    @Cacheable(value = "assignment", key = "'assignment'+#assignment.id+#boardId+#stageId+#cardId+#userName")
    public Object toResource(Assignment assignment, String boardId, String stageId, String cardId, String userName) throws Exception {
        logger.info("build assignment resource.boardId:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        AssignmentResource assignmentResource = new AssignmentResource();
        assignmentResource.domainObject = assignment;
        if (assignment != null) {
            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, stageId, cardId, userName)).withSelfRel();
            assignmentResource.add(tlink.from(assignmentsLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
            assignmentResource.add(tlink.from(cardLink).build(userName));

            Link AssigneeProfileLink = linkTo(methodOn(UsersController.class).loadProfile(assignment.getAssignee())).withRel("assigneeProfile");
            assignmentResource.add(tlink.from(AssigneeProfileLink).build(userName));
            Link AssigneeAvatarLink = linkTo(UsersController.class, UsersController.class.getMethod("loadAvatar", String.class, HttpServletResponse.class), assignment.getAssignee()).withRel("assigneeAvatar");
            assignmentResource.add(tlink.from(AssigneeAvatarLink).build(userName));
        }
        Link allLink = linkTo(methodOn(StagesController.class).loadAll(stageId, null, userName)).withRel("all");
        assignmentResource.add(tlink.from(allLink).build(userName));
        logger.info("assignment resource building completed.boardId:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        return assignmentResource.getResource();
    }

    @Cacheable(value = "assignment", key = "'assignment'+#boardId+#stageId+#cardId+#userName")
    public Object toResource(String boardId, String stageId, String cardId, String userName) throws Exception {
        logger.info("build assignment resource.boardId:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        AssignmentResource assignmentResource = new AssignmentResource();

        Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, stageId, cardId, userName)).withRel("assignments");
        assignmentResource.add(tlink.from(assignmentsLink).build(userName));

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
        assignmentResource.add(tlink.from(cardLink).build(userName));
        logger.info("assignment resource building completed.boardId:{},stageId:{},cardId:{},userName:{}", boardId, stageId, cardId, userName);
        return assignmentResource.getResource();
    }
}
