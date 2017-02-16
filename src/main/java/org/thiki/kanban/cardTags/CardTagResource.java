package org.thiki.kanban.cardTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by xubt on 11/14/16.
 */
@Service
public class CardTagResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(CardTagResource.class);

    @Resource
    private TLink tlink;

    @Cacheable(value = "card-tag", key = "#userName+'card-tag'+#boardId+#stageId+#cardId+#cardTag.id")
    public Object toResource(CardTag cardTag, String boardId, String stageId, String cardId, String userName) throws Exception {
        logger.info("build card tag resource.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        CardTagResource cardTagResource = new CardTagResource();
        cardTagResource.domainObject = cardTag;
        if (cardTag != null) {
            Link tagsLink = linkTo(methodOn(CardTagsController.class).stick(null, boardId, stageId, cardId, null)).withRel("tags");
            cardTagResource.add(tlink.from(tagsLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
            cardTagResource.add(tlink.from(cardLink).build(userName));
        }
        logger.info("card tag resource building completed.board:{},stageId:{},userName:{}", boardId, stageId, userName);
        return cardTagResource.getResource();
    }
}
