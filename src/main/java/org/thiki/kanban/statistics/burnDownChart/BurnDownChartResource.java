package org.thiki.kanban.statistics.burnDownChart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.page.Page;
import org.thiki.kanban.page.PageResource;
import org.thiki.kanban.page.PagesController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by winie on 2017/3/27.
 */
@Service
public class BurnDownChartResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(PageResource.class);
    @Resource
    private TLink tlink;

    public Object toResource(BurnDownChart burnDownChart, String projectId, String boardId, String sprintId,String userName) throws Exception {
        logger.info("build burnDownChart resource.projectId:{},board:{},sprintId:{},userName:{}",projectId, boardId,sprintId, userName);
        BurnDownChartResource burnDownChartResource = new BurnDownChartResource();
        burnDownChartResource.domainObject = burnDownChart;
        if (burnDownChart != null) {
            Link selfLink = linkTo(methodOn(BurnDownChartController.class).findBySprintIdAndBoardId(boardId,sprintId,projectId,userName)).withSelfRel();
            burnDownChartResource.add(tlink.from(selfLink).build(userName));
        }
        Link boardLink = linkTo(methodOn(BoardsController.class).findById(boardId, projectId, userName)).withRel("board");
        burnDownChartResource.add(tlink.from(boardLink).build(userName));

        logger.info("burnDownChart resource building completed..projectId:{},board:{},sprintId:{},userName:{}",projectId, boardId,sprintId, userName);
        return burnDownChartResource.getResource();
    }
}
