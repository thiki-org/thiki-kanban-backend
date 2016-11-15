package org.thiki.kanban.cardTags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

/**
 * Created by xubt on 11/14/16.
 */
@RestController
public class CardTagsController {

    @Autowired
    private CardTagsService cardTagsService;

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/tags", method = RequestMethod.POST)
    public HttpEntity stick(@RequestBody List<CardTag> cardTags, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        List<CardTag> stickCardTags = cardTagsService.stickTags(cardTags, cardId, userName);

        return Response.post(new CardTagsResource(stickCardTags, boardId, procedureId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/tags", method = RequestMethod.GET)
    public HttpEntity loadTags(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        List<CardTag> stickCardTags = cardTagsService.loadTags(cardId);

        return Response.build(new CardTagsResource(stickCardTags, boardId, procedureId, cardId, userName));
    }
}
