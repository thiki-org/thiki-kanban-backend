package org.thiki.kanban.statistics.burnDownChart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by winie on 2017/3/27.
 */
@Service
public class BurnDownChartsResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(BurnDownChartsResource.class);
    @Resource
    private TLink tlink;

    @Resource
    private BurnDownChartResource burnDownChartResourceService;

    public Object toResource(List<BurnDownChart> burnDownCharts, String projectId,  String boardId, String sprintId, String userName) throws Exception {
        logger.info("build BurnDownChart resource..projectId:{},board:{},sprintId:{},userName:{}",projectId, boardId,sprintId, userName);
        BurnDownChartsResource burnDownChartsResource = new BurnDownChartsResource();
        burnDownChartsResource.domainObject = burnDownCharts;

        List<Object> burnDownChartResources = new ArrayList<>();
        for (BurnDownChart burnDownChart : burnDownCharts) {
            Object burnDownChartResource = burnDownChartResourceService.toResource(burnDownChart,projectId, boardId, sprintId, userName);
            burnDownChartResources.add(burnDownChartResource);
        }
        burnDownChartsResource.buildDataObject("burnDownCharts", burnDownChartResources);

        Link boardLink = linkTo(methodOn(BoardsController.class).findById(boardId, projectId, userName)).withRel("board");
        burnDownChartsResource.add(tlink.from(boardLink).build(userName));

        logger.info("BurnDownChart resource building completed..projectId:{},board:{},sprintId:{},userName:{}",projectId, boardId,sprintId, userName);
        return burnDownChartsResource.getResource();
    }
}

