package org.thiki.kanban.assignment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.entry.EntriesController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.card.CardsController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 6/16/16.
 */
public class AssignmentResource extends RestResource {
    public AssignmentResource(Assignment assignment, String entryId, String cardId) {
        this.domainObject = assignment;
        if (assignment != null) {
            Link selfLink = linkTo(methodOn(AssignmentController.class).findById(entryId, cardId, assignment.getId())).withSelfRel();
            this.add(selfLink);

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(entryId, cardId)).withRel("assignments");
            this.add(assignmentsLink);

            Link cardLink = linkTo(methodOn(CardsController.class).findById(entryId, cardId)).withRel("card");
            this.add(cardLink);
        }
        this.add(linkTo(methodOn(EntriesController.class).loadAll(entryId)).withRel("all"));
    }

    public AssignmentResource(String entryId, String cardId) {
        Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(entryId, cardId)).withRel("assignments");
        this.add(assignmentsLink);

        Link cardLink = linkTo(methodOn(CardsController.class).findById(entryId, cardId)).withRel("card");
        this.add(cardLink);
    }
}
