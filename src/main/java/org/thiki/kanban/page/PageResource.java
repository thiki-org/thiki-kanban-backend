package org.thiki.kanban.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaController;
import org.thiki.kanban.assignment.AssignmentController;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.cardTags.CardTagsController;
import org.thiki.kanban.comment.CommentController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.tag.TagsController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 页面资源
 *
 * @author joeaniu
 */

@Service
public class PageResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(PageResource.class);
    @Resource
    private TLink tLink;

    @Cacheable(value = "page", key = "#userName+#boardId")
    public Object toResource(String boardId, String userName) throws Exception {
        logger.info("build page resource.board:{},,userName:{}", boardId,  userName);
        PageResource pageResource = new PageResource();
        Link pageLink = linkTo(methodOn(PagesController.class).findByBoardId(boardId,userName)).withRel("pages");
        pageResource.add(tLink.from(pageLink).build(userName));
        logger.info("page resource building completed.board:{},userName:{}", boardId, userName);
        return pageResource.getResource();
    }
}
