package org.thiki.kanban.cardTags;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by xubt on 11/14/16.
 */
public class CardTagResource extends RestResource {
    public CardTagResource(CardTag cardTag, String boardId, String procedureId, String cardId) throws Exception {
        this.domainObject = cardTag;
        if (cardTag != null) {

            Link tagsLink = linkTo(methodOn(CardTagsController.class).stick(null, boardId, procedureId, cardId, null)).withRel("tags");
            this.add(tagsLink);

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId)).withRel("card");
            this.add(cardLink);
        }
    }
}
