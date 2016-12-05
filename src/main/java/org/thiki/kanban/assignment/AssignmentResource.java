package org.thiki.kanban.assignment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.procedure.ProceduresController;
import org.thiki.kanban.user.UsersController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 6/16/16.
 */
public class AssignmentResource extends RestResource {
    public AssignmentResource(Assignment assignment, String boardId, String procedureId, String cardId) throws Exception {
        this.domainObject = assignment;
        if (assignment != null) {
            Link selfLink = linkTo(methodOn(AssignmentController.class).findById(boardId, procedureId, cardId, assignment.getId())).withSelfRel();
            this.add(selfLink);

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, procedureId, cardId)).withRel("assignments");
            this.add(assignmentsLink);

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId)).withRel("card");
            this.add(cardLink);

            Link AssigneeProfileLink = linkTo(methodOn(UsersController.class).loadProfile(assignment.getAssignee())).withRel("assigneeProfile");
            this.add(AssigneeProfileLink);

            Link AssigneeAvatarLink = linkTo(methodOn(UsersController.class).loadAvatar(assignment.getAssignee())).withRel("assigneeAvatar");
            this.add(AssigneeAvatarLink);
        }
        this.add(linkTo(methodOn(ProceduresController.class).loadAll(procedureId)).withRel("all"));
    }

    public AssignmentResource(String boardId, String procedureId, String cardId) throws Exception {
        Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, procedureId, cardId)).withRel("assignments");
        this.add(assignmentsLink);

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId)).withRel("card");
        this.add(cardLink);
    }
}
