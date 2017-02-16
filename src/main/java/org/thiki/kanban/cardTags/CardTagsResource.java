package org.thiki.kanban.cardTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.card.CardsResource;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 11/14/16.
 */
@Service
public class CardTagsResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(CardTagsResource.class);

    @Resource
    private TLink tlink;
    @Resource
    private CardTagResource cardTagResourceService;

    @Cacheable(value = "card-tag", key = "#userName+'card-tags'+#boardId+#stageId+#cardId")
    public Object toResource(List<CardTag> cardTags, String boardId, String stageId, String cardId, String userName) throws Exception {
        logger.info("build cards tag resource.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        CardsResource cardsResource = new CardsResource();
        List<Object> cardTagResources = new ArrayList<>();
        for (CardTag cardTag : cardTags) {
            Object cardTagResource = cardTagResourceService.toResource(cardTag, boardId, stageId, cardId, userName);
            cardTagResources.add(cardTagResource);
        }

        cardsResource.buildDataObject("cardTags", cardTagResources);

        Link selfLink = linkTo(methodOn(CardTagsController.class).stick(null, boardId, stageId, cardId, null)).withSelfRel();
        cardsResource.add(tlink.from(selfLink).build(userName));

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
        cardsResource.add(tlink.from(cardLink).build(userName));

        Link boardLink = linkTo(methodOn(BoardsController.class).findById(boardId, userName)).withRel("board");
        cardsResource.add(tlink.from(boardLink).build(userName));
        logger.info("cards tag resource building completed.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        return cardsResource.getResource();
    }
}
