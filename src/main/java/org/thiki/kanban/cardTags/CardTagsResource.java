package org.thiki.kanban.cardTags;

import org.springframework.hateoas.Link;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 11/14/16.
 */
public class CardTagsResource extends RestResource {
    public CardTagsResource(List<CardTag> cardTags, String procedureId, String cardId) throws IOException {
        List<CardTagResource> cardTagResources = new ArrayList<>();
        for (CardTag cardTag : cardTags) {
            CardTagResource cardTagResource = new CardTagResource(cardTag, procedureId, cardId);
            cardTagResources.add(cardTagResource);
        }

        this.buildDataObject("cardTags", cardTagResources);

        Link selfLink = linkTo(methodOn(CardTagsController.class).stick(null, procedureId, cardId, null)).withSelfRel();
        this.add(selfLink);

        Link cardLink = linkTo(methodOn(CardsController.class).findById(procedureId, cardId)).withRel("card");
        this.add(cardLink);
    }
}
