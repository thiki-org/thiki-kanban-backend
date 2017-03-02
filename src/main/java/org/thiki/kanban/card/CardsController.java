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

    @RequestMapping(value = "/boards/{boardId}/cards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Card card, @RequestHeader String userName, @PathVariable String boardId) throws Exception {
        Card savedCard = cardsService.saveCard(userName, boardId, card);
        return Response.post(cardResource.toResource(savedCard, boardId, savedCard.getStageId(), userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards", method = RequestMethod.GET)
    public HttpEntity findByStageId(@PathVariable String boardId, @PathVariable String stageId, @RequestHeader String userName) throws Exception {
        logger.info("Loading cards by stageId [{}]", stageId);
        List<Card> cardList = cardsService.findByStageId(stageId);
        return Response.build(cardsResource.toResource(cardList, boardId, stageId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        Card foundCard = cardsService.findById(cardId);

        return Response.build(cardResource.toResource(foundCard, boardId, stageId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Card card, @PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        Card updatedCard = cardsService.modify(cardId, card, stageId, boardId, userName);
        return Response.build(cardResource.toResource(updatedCard, boardId, stageId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        cardsService.deleteById(cardId);
        return Response.build(cardResource.toResource(boardId, stageId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/sortNumbers", method = RequestMethod.PUT)
    public HttpEntity resortCards(@RequestBody List<Card> cards, @PathVariable String boardId, @PathVariable String stageId, @RequestHeader String userName) throws Exception {
        List<Card> sortedCards = cardsService.resortCards(cards, stageId, boardId, userName);

        return Response.build(cardsResource.toResource(sortedCards, boardId, stageId, userName));
    }
}
