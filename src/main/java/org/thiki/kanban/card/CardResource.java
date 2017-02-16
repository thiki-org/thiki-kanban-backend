package org.thiki.kanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
    public static Logger logger = LoggerFactory.getLogger(CardResource.class);

    @Resource
    private TLink tLink;

    @Cacheable(value = "card", key = "#userName+#boardId+#stageId+#card.id")
    public Object toResource(Card card, String boardId, String stageId, String userName) throws Exception {
        logger.info("build card resource.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        CardResource cardResource = new CardResource();
        cardResource.domainObject = card;
        if (card != null) {
            Link selfLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, card.getId(), userName)).withSelfRel();
            cardResource.add(tLink.from(selfLink).build(userName));

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByCardId(boardId, stageId, card.getId(), userName)).withRel("assignments");
            cardResource.add(tLink.from(assignmentsLink).build(userName));

            Link acceptanceCriteriasLink = linkTo(methodOn(AcceptanceCriteriaController.class).loadAcceptanceCriteriasByCardId(boardId, stageId, card.getId(), userName)).withRel("acceptanceCriterias");
            cardResource.add(tLink.from(acceptanceCriteriasLink).build(userName));

            Link commentsLink = linkTo(methodOn(CommentController.class).loadCommentsByCardId(boardId, stageId, card.getId(), userName)).withRel("comments");
            cardResource.add(tLink.from(commentsLink).build(userName));

            Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByBoard(boardId, userName)).withRel("tags");
            cardResource.add(tLink.from(tagsLink).build(userName));

            Link cardTagsLink = linkTo(methodOn(CardTagsController.class).stick(null, boardId, stageId, card.getId(), null)).withRel("cardTags");
            cardResource.add(tLink.from(cardTagsLink).build(userName));
        }
        Link cardsLink = linkTo(methodOn(CardsController.class).findByStageId(boardId, stageId, userName)).withRel("cards");
        cardResource.add(tLink.from(cardsLink).build(userName));
        logger.info("card resource building completed.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        return cardResource.getResource();
    }

    @Cacheable(value = "card", key = "#userName+#boardId+#stageId")
    public Object toResource(String boardId, String stageId, String userName) throws Exception {
        logger.info("build card resource.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        CardResource cardResource = new CardResource();
        Link cardsLink = linkTo(methodOn(CardsController.class).findByStageId(boardId, stageId, userName)).withRel("cards");
        cardResource.add(tLink.from(cardsLink).build(userName));
        logger.info("card resource building completed.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        return cardResource.getResource();
    }
}
