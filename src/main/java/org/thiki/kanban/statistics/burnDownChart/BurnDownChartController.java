package org.thiki.kanban.statistics.burnDownChart;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by winie on 2017/3/27.
 */
@RestController
@RequestMapping(value = "")
public class BurnDownChartController {
    @Resource
    private BurnDownChartService burnDownChartService;
    @Resource
    private BurnDownChartsResource burnDownChartsResource;

    @RequestMapping(value = "/statistics/projects/{projectId}/boards/{boardId}/sprint/{sprintId}", method = RequestMethod.GET)
    public HttpEntity findBySprintIdAndBoardId(@PathVariable String boardId, @PathVariable String sprintId,@PathVariable String projectId, @RequestHeader String userName) throws Exception {
        List<BurnDownChart> burnDownCharts=burnDownChartService.findBurnDownChartBySprintIdAndBoardId(boardId,sprintId);
        return Response.build(burnDownChartsResource.toResource(burnDownCharts, projectId,boardId,sprintId, userName));
    }
}
