package org.thiki.kanban.card;

import org.springframework.hateoas.Link;
import org.thiki.kanban.acceptanceCriteria.AcceptCriteriaController;
import org.thiki.kanban.assignment.AssignmentController;
import org.thiki.kanban.cardTags.CardTagsController;
import org.thiki.kanban.comment.CommentController;
import org.thiki.kanban.foundation.common.RestResource;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 卡片的资源DTO
 *
 * @author joeaniu
 */
public class CardResource extends RestResource {
    public CardResource(Card card, String procedureId) throws IOException {
        this.domainObject = card;
        if (card != null) {
            Link selfLink = linkTo(methodOn(CardsController.class).findById(procedureId, card.getId())).withSelfRel();
            this.add(selfLink);

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(procedureId, card.getId())).withRel("assignments");
            this.add(assignmentsLink);

            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptCriteriaController.class).loadAcceptanceCriteriasByCardId(card.getId(), procedureId)).withRel("acceptanceCriterias");
            this.add(acceptanceCriteriasLink);

            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(card.getId(), procedureId)).withRel("comments");
            this.add(commentsLink);

            Link cardTagsLink = linkTo(methodOn(CardTagsController.class).stick(null, procedureId, card.getId(), null)).withRel("cardTags");
            this.add(cardTagsLink);
        }
        this.add(linkTo(methodOn(CardsController.class).findByProcedureId(procedureId)).withRel("cards"));

    }

    public CardResource(String procedureId) throws IOException {
        this.add(linkTo(methodOn(CardsController.class).findByProcedureId(procedureId)).withRel("cards"));
    }
}
