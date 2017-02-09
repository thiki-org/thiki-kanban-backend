package org.thiki.kanban.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by winie on 2017/2/6.
 */
@RestController
@RequestMapping(value = "")
public class PagesController {
    private static Logger logger = LoggerFactory.getLogger(PagesController.class);

    @Autowired
    private PagesService pagesService;


    @RequestMapping(value = "/boards/{boardId}/pages", method = RequestMethod.GET)
    public HttpEntity findByBoardId(@PathVariable String boardId, @RequestHeader String userName) throws Exception {
        logger.info("Loading pages by boardId [{}]", boardId);
        List<Page> pageList = pagesService.findByBoardId(boardId);
        //return Response.build(cardsResource.toResource(pageList, boardId, userName));
        return null;
    }

}
