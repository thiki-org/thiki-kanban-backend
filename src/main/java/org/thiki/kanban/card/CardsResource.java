package org.thiki.kanban.card;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 */
@Service
public class CardsResource extends RestResource {
    @Resource
    private TLink tLink;
    @Resource
    private CardResource cardResourceService;

    public Object toResource(List<Card> cardList, String boardId, String procedureId, String userName) throws Exception {
        CardsResource cardsResource = new CardsResource();
        List<Object> cardResources = new ArrayList<>();
        for (Card card : cardList) {
            Object cardResource = cardResourceService.toResource(card, boardId, procedureId, userName);
            cardResources.add(cardResource);
        }

        cardsResource.buildDataObject("cards", cardResources);
        Link selfLink = linkTo(methodOn(CardsController.class).findByProcedureId(boardId, procedureId, userName)).withSelfRel();
        cardsResource.add(tLink.from(selfLink).build(userName));

        Link sortNumbersLink = linkTo(methodOn(CardsController.class).resortCards(cardList, boardId, procedureId, userName)).withRel("sortNumbers");
        cardsResource.add(tLink.from(sortNumbersLink).build(userName));
        return cardsResource.getResource();
    }
}
