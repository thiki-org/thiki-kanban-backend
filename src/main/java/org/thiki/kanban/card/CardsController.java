package org.thiki.kanban.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
@RequestMapping(value = "")
public class CardsController {

    @Autowired
    private CardsService cardsService;

    @RequestMapping(value = "/procedures/{procedureId}/cards", method = RequestMethod.GET)
    public HttpEntity findByProcedureId(@PathVariable String procedureId) {
        List<Card> cardList = cardsService.findByProcedureId(procedureId);
        return Response.build(new CardsResource(cardList, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String procedureId, @PathVariable String id) {
        Card foundCard = cardsService.findById(id);

        return Response.build(new CardResource(foundCard, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Card card, @PathVariable String procedureId, @PathVariable String cardId) {
        Card updatedCard = cardsService.update(cardId, card);
        return Response.build(new CardResource(updatedCard, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String procedureId, @PathVariable String id) {
        cardsService.deleteById(id);
        return Response.build(new CardResource(procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Card card, @RequestHeader String userName, @PathVariable String procedureId) {
        Card savedCard = cardsService.create(userName, procedureId, card);

        return Response.post(new CardResource(savedCard, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/sortNumbers", method = RequestMethod.PUT)
    public HttpEntity resortCards(@RequestBody List<Card> cards, @PathVariable String procedureId) {
        List<Card> sortedCards = cardsService.resortCards(cards, procedureId);

        return Response.build(new CardsResource(sortedCards, procedureId));
    }
}
