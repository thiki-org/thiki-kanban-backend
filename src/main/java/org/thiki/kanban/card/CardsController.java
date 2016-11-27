package org.thiki.kanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(CardsController.class);

    @Autowired
    private CardsService cardsService;

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards", method = RequestMethod.GET)
    public HttpEntity findByProcedureId(@PathVariable String boardId, @PathVariable String procedureId) throws Exception {
        logger.info("Loading cards by procedureId [%s]", procedureId);
        List<Card> cardList = cardsService.findByProcedureId(procedureId);
        return Response.build(new CardsResource(cardList, boardId, procedureId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId) throws Exception {
        Card foundCard = cardsService.findById(cardId);

        return Response.build(new CardResource(foundCard, boardId, procedureId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Card card, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId) throws Exception {
        Card updatedCard = cardsService.update(cardId, card);
        return Response.build(new CardResource(updatedCard, boardId, procedureId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId) throws Exception {
        cardsService.deleteById(cardId);
        return Response.build(new CardResource(boardId, procedureId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Card card, @RequestHeader String userName, @PathVariable String boardId, @PathVariable String procedureId) throws Exception {
        Card savedCard = cardsService.create(userName, procedureId, card);

        return Response.post(new CardResource(savedCard, boardId, procedureId));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/sortNumbers", method = RequestMethod.PUT)
    public HttpEntity resortCards(@RequestBody List<Card> cards, @PathVariable String boardId, @PathVariable String procedureId) throws Exception {
        List<Card> sortedCards = cardsService.resortCards(cards, procedureId);

        return Response.build(new CardsResource(sortedCards, boardId, procedureId));
    }
}
