package org.thiki.kanban.card;

import org.springframework.hateoas.Link;
import org.thiki.kanban.assignment.AssignmentController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 卡片的资源DTO
 *
 * @author joeaniu
 */
public class CardResource extends RestResource {
    public CardResource(Card card, String entryId) {
        this.domainObject = card;
        if (card != null) {
            Link selfLink = linkTo(methodOn(CardsController.class).findById(entryId, card.getId())).withSelfRel();
            this.add(selfLink);

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(entryId, card.getId())).withRel("assignments");
            this.add(assignmentsLink);
        }

        this.add(linkTo(methodOn(CardsController.class).findByEntryId(entryId)).withRel("cards"));

    }

    public CardResource(String entryId) {
        this.add(linkTo(methodOn(CardsController.class).findByEntryId(entryId)).withRel("cards"));
    }
}
