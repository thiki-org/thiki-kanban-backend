package org.thiki.kanban.card;

import org.springframework.hateoas.Link;
import org.thiki.kanban.acceptanceCriteria.AcceptCriteriaController;
import org.thiki.kanban.assignment.AssignmentController;
import org.thiki.kanban.cardTags.CardTagsController;
import org.thiki.kanban.comment.CommentController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.tag.TagsController;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 卡片的资源DTO
 *
 * @author joeaniu
 */
public class CardResource extends RestResource {
    public CardResource(Card card, String boardId, String procedureId) throws IOException {
        this.domainObject = card;
        if (card != null) {
            Link selfLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, card.getId())).withSelfRel();
            this.add(selfLink);

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, procedureId, card.getId())).withRel("assignments");
            this.add(assignmentsLink);

            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, procedureId, card.getId())).withRel("acceptanceCriterias");
            this.add(acceptanceCriteriasLink);

            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, procedureId, card.getId())).withRel("comments");
            this.add(commentsLink);

            Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByBoard(boardId)).withRel("tags");
            this.add(tagsLink);

            Link cardTagsLink = linkTo(methodOn(CardTagsController.class).stick(null, boardId, procedureId, card.getId(), null)).withRel("cardTags");
            this.add(cardTagsLink);
        }
        this.add(linkTo(methodOn(CardsController.class).findByProcedureId(boardId, procedureId)).withRel("cards"));

    }

    public CardResource(String boardId, String procedureId) throws IOException {
        this.add(linkTo(methodOn(CardsController.class).findByProcedureId(boardId, procedureId)).withRel("cards"));
    }
}
