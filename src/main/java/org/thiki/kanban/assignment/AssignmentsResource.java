package org.thiki.kanban.assignment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 6/16/16.
 */
public class AssignmentsResource extends RestResource {
    public AssignmentsResource(List<Assignment> assignmentList, String boardId, String procedureId, String cardId) throws IOException {
        List<AssignmentResource> assignmentResources = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            AssignmentResource assignmentResource = new AssignmentResource(assignment, boardId, procedureId, cardId);
            assignmentResources.add(assignmentResource);
        }

        this.buildDataObject("assignments", assignmentResources);
        Link selfLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId,procedureId, cardId)).withSelfRel();
        this.add(selfLink);

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId)).withRel("card");
        this.add(cardLink);
    }
}
