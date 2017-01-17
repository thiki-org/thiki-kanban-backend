package org.thiki.kanban.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
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

    @Resource
    private CardResource cardResource;
    @Resource
    private CardsResource cardsResource;

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards", method = RequestMethod.GET)
    public HttpEntity findByProcedureId(@PathVariable String boardId, @PathVariable String procedureId, @RequestHeader String userName) throws Exception {
        logger.info("Loading cards by procedureId [{}]", procedureId);
        List<Card> cardList = cardsService.findByProcedureId(procedureId);
        return Response.build(cardsResource.toResource(cardList, boardId, procedureId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        Card foundCard = cardsService.findById(cardId);

        return Response.build(cardResource.toResource(foundCard, boardId, procedureId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Card card, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        Card updatedCard = cardsService.modify(cardId, card, procedureId, userName);
        return Response.build(cardResource.toResource(updatedCard, boardId, procedureId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        cardsService.deleteById(cardId);
        return Response.build(cardResource.toResource(boardId, procedureId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Card card, @RequestHeader String userName, @PathVariable String boardId, @PathVariable String procedureId) throws Exception {
        Card savedCard = cardsService.create(userName, procedureId, card);

        return Response.post(cardResource.toResource(savedCard, boardId, procedureId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/sortNumbers", method = RequestMethod.PUT)
    public HttpEntity resortCards(@RequestBody List<Card> cards, @PathVariable String boardId, @PathVariable String procedureId, @RequestHeader String userName) throws Exception {
        List<Card> sortedCards = cardsService.resortCards(cards, procedureId, boardId);

        return Response.build(cardsResource.toResource(sortedCards, boardId, procedureId, userName));
    }
}
