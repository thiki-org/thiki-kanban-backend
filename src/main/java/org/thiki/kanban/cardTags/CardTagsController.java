package org.thiki.kanban.cardTags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by xubt on 11/14/16.
 */
@RestController
public class CardTagsController {

    @Autowired
    private CardTagsService cardTagsService;

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/tags", method = RequestMethod.POST)
    public HttpEntity stick(@RequestBody List<CardTag> cardTags, @PathVariable("procedureId") String procedureId, @PathVariable("cardId") String cardId, @RequestHeader String userName) throws IOException {
        List<CardTag> stickCardTags = cardTagsService.stickTags(cardTags, cardId, userName);

        return Response.post(new CardTagsResource(stickCardTags, procedureId, cardId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/tags/{tagId}", method = RequestMethod.POST)
    public HttpEntity findById(@PathVariable("procedureId") String procedureId, @PathVariable("cardId") String cardId, @PathVariable("tagId") String tagId) {
        return null;
    }
}
