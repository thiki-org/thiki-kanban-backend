package org.thiki.kanban.assignment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.procedure.ProceduresController;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 6/16/16.
 */
public class AssignmentResource extends RestResource {
    public AssignmentResource(Assignment assignment, String procedureId, String cardId) throws IOException {
        this.domainObject = assignment;
        if (assignment != null) {
            Link selfLink = linkTo(methodOn(AssignmentController.class).findById(procedureId, cardId, assignment.getId())).withSelfRel();
            this.add(selfLink);

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(procedureId, cardId)).withRel("assignments");
            this.add(assignmentsLink);

            Link cardLink = linkTo(methodOn(CardsController.class).findById(procedureId, cardId)).withRel("card");
            this.add(cardLink);
        }
        this.add(linkTo(methodOn(ProceduresController.class).loadAll(procedureId)).withRel("all"));
    }

    public AssignmentResource(String procedureId, String cardId) throws IOException {
        Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(procedureId, cardId)).withRel("assignments");
        this.add(assignmentsLink);

        Link cardLink = linkTo(methodOn(CardsController.class).findById(procedureId, cardId)).withRel("card");
        this.add(cardLink);
    }
}
