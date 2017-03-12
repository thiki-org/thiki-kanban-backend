package org.thiki.kanban.board.snapshot;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubitao on 05/26/16.
 */
@RestController
public class BoardsSnapshotController {
    @Resource
    private SnapshotService snapshotService;

    @RequestMapping(value = "/{userName}/projects/{projectId}/boards/{boardId}/snapshot", method = RequestMethod.GET)
    public HttpEntity load(@PathVariable String projectId, @PathVariable String boardId, @RequestParam(required = false) String viewType, @PathVariable String userName) throws Exception {
        Object board = snapshotService.loadSnapshotByBoard(projectId, boardId, viewType, userName);
        return Response.build(board);
    }
}
