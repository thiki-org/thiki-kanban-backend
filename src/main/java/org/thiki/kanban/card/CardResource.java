package org.thiki.kanban.card;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaController;
import org.thiki.kanban.assignment.AssignmentController;
import org.thiki.kanban.cardTags.CardTagsController;
import org.thiki.kanban.comment.CommentController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.tag.TagsController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 卡片的资源DTO
 *
 * @author joeaniu
 */

@Service
public class CardResource extends RestResource {
    @Resource
    private TLink tLink;

    public Object toResource(Card card, String boardId, String procedureId, String userName) throws Exception {

        CardResource cardResource = new CardResource();
        cardResource.domainObject = card;
        if (card != null) {
            Link selfLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, card.getId(), userName)).withSelfRel();
            cardResource.add(tLink.from(selfLink).build(userName));

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, procedureId, card.getId(), userName)).withRel("assignments");
            cardResource.add(tLink.from(assignmentsLink).build(userName));

            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptanceCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, procedureId, card.getId(), userName)).withRel("acceptanceCriterias");
            cardResource.add(tLink.from(acceptanceCriteriasLink).build(userName));

            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, procedureId, card.getId(), userName)).withRel("comments");
            cardResource.add(tLink.from(commentsLink).build(userName));

            Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByBoard(boardId, userName)).withRel("tags");
            cardResource.add(tLink.from(tagsLink).build(userName));

            Link cardTagsLink = linkTo(methodOn(CardTagsController.class).stick(null, boardId, procedureId, card.getId(), null)).withRel("cardTags");
            cardResource.add(tLink.from(cardTagsLink).build(userName));
        }
        Link cardsLink = linkTo(methodOn(CardsController.class).findByProcedureId(boardId, procedureId, userName)).withRel("cards");
        cardResource.add(tLink.from(cardsLink).build(userName));
        return cardResource.getResource();
    }

    public Object toResource(String boardId, String procedureId, String userName) throws Exception {
        CardResource cardResource = new CardResource();
        Link cardsLink = linkTo(methodOn(CardsController.class).findByProcedureId(boardId, procedureId, userName)).withRel("cards");
        cardResource.add(tLink.from(cardsLink).build(userName));
        return cardResource.getResource();
    }
}
