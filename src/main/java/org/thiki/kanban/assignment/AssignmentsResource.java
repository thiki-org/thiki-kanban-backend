package org.thiki.kanban.assignment;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 6/16/16.
 */
@Service
public class AssignmentsResource extends RestResource {
    @Resource
    private TLink tlink;
    @Resource
    private AssignmentResource assignmentResourceService;

    public Object toResource(List<Assignment> assignmentList, String boardId, String procedureId, String cardId, String userName) throws Exception {
        AssignmentsResource assignmentsResource = new AssignmentsResource();
        List<Object> assignmentResources = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            Object assignmentResource = assignmentResourceService.toResource(assignment, boardId, procedureId, cardId, userName);
            assignmentResources.add(assignmentResource);
        }

        assignmentsResource.buildDataObject("assignments", assignmentResources);
        Link selfLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, procedureId, cardId, userName)).withSelfRel();
        assignmentsResource.add(tlink.from(selfLink).build(userName));

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId, userName)).withRel("card");
        assignmentsResource.add(tlink.from(cardLink).build(userName));
        return assignmentsResource.getResource();
    }
}
