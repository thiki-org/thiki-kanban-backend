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

    @RequestMapping(value = "/entries/{entryId}/cards", method = RequestMethod.GET)
    public HttpEntity findByEntryId(@PathVariable String entryId) {
        List<Card> cardList = cardsService.findByEntryId(entryId);
        return Response.build(new CardsResource(cardList, entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/cards/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String entryId, @PathVariable String id) {
        Card foundCard = cardsService.findById(id);

        return Response.build(new CardResource(foundCard, entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/cards/{cardId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Card card, @PathVariable String entryId, @PathVariable String cardId) {
        Card updatedCard = cardsService.update(cardId, card);
        return Response.build(new CardResource(updatedCard, entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/cards/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String entryId, @PathVariable String id) {
        cardsService.deleteById(id);
        return Response.build(new CardResource(entryId));
    }

    @RequestMapping(value = "/entries/{entryId}/cards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Card card, @PathVariable String entryId, @RequestHeader Integer userId) {
        Card savedCard = cardsService.create(userId, entryId, card);

        return Response.post(new CardResource(savedCard, entryId));
    }
}
