package org.thiki.kanban.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 02/11/17.
 */

@Service
public class PagesResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(PagesResource.class);
    @Resource
    private TLink tlink;

    @Resource
    private PageResource pageResourceService;

    @Cacheable(value = "page", key = "'pages'+#userName+#boardId")
    public Object toResource(List<Page> pages, String boardId, String userName) throws Exception {
        logger.info("build pages resource.board:{},,userName:{}", boardId, userName);
        PagesResource pagesResource = new PagesResource();
        pagesResource.domainObject = pages;

        List<Object> pageResources = new ArrayList<>();
        for (Page page : pages) {
            Object pageResource = pageResourceService.toResource(page, boardId, userName);
            pageResources.add(pageResource);
        }
        pagesResource.buildDataObject("pages", pageResources);

        Link boardLink = linkTo(methodOn(BoardsController.class).findById(boardId, userName)).withRel("board");
        pagesResource.add(tlink.from(boardLink).build(userName));

        Link selfLink = linkTo(methodOn(PagesController.class).findByBoard(boardId, userName)).withSelfRel();
        pagesResource.add(tlink.from(selfLink).build(userName));
        logger.info("pages resource building completed.board:{},userName:{}", boardId, userName);
        return pagesResource.getResource();
    }
}
