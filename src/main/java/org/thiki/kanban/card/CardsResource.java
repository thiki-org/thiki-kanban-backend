package org.thiki.kanban.card;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 */
public class CardsResource extends RestResource {
    public CardsResource(List<Card> cardList, String procedureId) {

        List<CardResource> cardResources = new ArrayList<>();
        for (Card card : cardList) {
            CardResource cardResource = new CardResource(card, procedureId);
            cardResources.add(cardResource);
        }

        this.buildDataObject("cards", cardResources);
        Link selfLink = linkTo(methodOn(CardsController.class).findByProcedureId(procedureId)).withSelfRel();
        this.add(selfLink);

        Link sortNumbersLink = linkTo(methodOn(CardsController.class).resortCards(cardList, procedureId)).withRel("sortNumbers");
        this.add(sortNumbersLink);
    }
}
