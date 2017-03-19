package org.thiki.kanban.cardTags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 11/14/16.
 */
@RestController
public class CardTagsController {
    @Autowired
    private CardTagsService cardTagsService;
    @Resource
    private CardTagsResource cardTagsResource;

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/tags", method = RequestMethod.POST)
    public HttpEntity stick(@RequestBody List<CardTag> cardTags, @PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        List<CardTag> stickCardTags = cardTagsService.stickTags(cardTags, cardId, boardId, userName);

        return Response.post(cardTagsResource.toResource(stickCardTags, boardId, stageId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/tags", method = RequestMethod.GET)
    public HttpEntity loadTags(@PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        List<CardTag> stickCardTags = cardTagsService.loadTags(cardId, boardId);

        return Response.build(cardTagsResource.toResource(stickCardTags, boardId, stageId, cardId, userName));
    }
}
