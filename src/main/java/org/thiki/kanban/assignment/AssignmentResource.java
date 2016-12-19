package org.thiki.kanban.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.procedure.ProceduresController;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;

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

    public Object toResource(Assignment assignment, String boardId, String procedureId, String cardId, String userName) throws Exception {
        logger.info("build assignment resource.boardId:{},procedureId:{},cardId:{},userName:{}", boardId, procedureId, cardId, userName);
        AssignmentResource assignmentResource = new AssignmentResource();
        assignmentResource.domainObject = assignment;
        if (assignment != null) {
            Link selfLink = linkTo(methodOn(AssignmentController.class).findById(boardId, procedureId, cardId, assignment.getId(), userName)).withSelfRel();
            assignmentResource.add(tlink.from(selfLink).build(userName));

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, procedureId, cardId, userName)).withRel("assignments");
            assignmentResource.add(tlink.from(assignmentsLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId, userName)).withRel("card");
            assignmentResource.add(tlink.from(cardLink).build(userName));

            Link AssigneeProfileLink = linkTo(methodOn(UsersController.class).loadProfile(assignment.getAssignee())).withRel("assigneeProfile");
            assignmentResource.add(tlink.from(AssigneeProfileLink).build(userName));

            Link AssigneeAvatarLink = linkTo(methodOn(UsersController.class).loadAvatar(assignment.getAssignee())).withRel("assigneeAvatar");
            assignmentResource.add(tlink.from(AssigneeAvatarLink).build(userName));
        }
        Link allLink = linkTo(methodOn(ProceduresController.class).loadAll(procedureId, userName)).withRel("all");
        assignmentResource.add(tlink.from(allLink).build(userName));
        logger.info("assignment resource building completed.boardId:{},procedureId:{},cardId:{},userName:{}", boardId, procedureId, cardId, userName);
        return assignmentResource.getResource();
    }

    public Object toResource(String boardId, String procedureId, String cardId, String userName) throws Exception {
        logger.info("build assignment resource.boardId:{},procedureId:{},cardId:{},userName:{}", boardId, procedureId, cardId, userName);
        AssignmentResource assignmentResource = new AssignmentResource();

        Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, procedureId, cardId, userName)).withRel("assignments");
        assignmentResource.add(tlink.from(assignmentsLink).build(userName));

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId, userName)).withRel("card");
        assignmentResource.add(tlink.from(cardLink).build(userName));
        logger.info("assignment resource building completed.boardId:{},procedureId:{},cardId:{},userName:{}", boardId, procedureId, cardId, userName);
        return assignmentResource.getResource();
    }
}
