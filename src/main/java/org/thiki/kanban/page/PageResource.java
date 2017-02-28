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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 02/11/17.
 */

@Service
public class PageResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(PageResource.class);
    @Resource
    private TLink tlink;

    @Cacheable(value = "page", key = "#userName+#boardId+#page.id")
    public Object toResource(Page page, String boardId, String projectId, String userName) throws Exception {
        logger.info("build page resource.board:{},,userName:{}", boardId, userName);
        PageResource pageResource = new PageResource();
        pageResource.domainObject = page;
        if (page != null) {
            Link selfLink = linkTo(methodOn(PagesController.class).findById(boardId, page.getId(), projectId, userName)).withSelfRel();
            pageResource.add(tlink.from(selfLink).build(userName));
        }
        Link boardLink = linkTo(methodOn(BoardsController.class).findById(boardId, projectId, userName)).withRel("board");
        pageResource.add(tlink.from(boardLink).build(userName));

        Link pagesLink = linkTo(methodOn(PagesController.class).findByBoard(boardId, projectId, userName)).withRel("pages");
        pageResource.add(tlink.from(pagesLink).build(userName));
        logger.info("page resource building completed.board:{},userName:{}", boardId, userName);
        return pageResource.getResource();
    }

    public Object toResource(String boardId, String projectId, String userName) throws Exception {
        logger.info("build page resource.board:{},,userName:{}", boardId, userName);
        PageResource pageResource = new PageResource();

        Link boardLink = linkTo(methodOn(BoardsController.class).findById(boardId, projectId, userName)).withRel("board");
        pageResource.add(tlink.from(boardLink).build(userName));

        Link pagesLink = linkTo(methodOn(PagesController.class).findByBoard(boardId, projectId, userName)).withRel("pages");
        pageResource.add(tlink.from(pagesLink).build(userName));
        logger.info("page resource building completed.board:{},userName:{}", boardId, userName);
        return pageResource.getResource();
    }
}
