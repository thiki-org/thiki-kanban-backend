package org.thiki.kanban.board.snapshot;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubitao on 05/26/16.
 */
@RestController
public class BoardsSnapshotController {
    @Resource
    private SnapshotService snapshotService;

    @RequestMapping(value = "/{userName}/boards/{boardId}/snapshot", method = RequestMethod.GET)
    public HttpEntity load(@PathVariable String boardId, @PathVariable String userName) throws Exception {
        Object board = snapshotService.loadAllByBoard(boardId, userName);
        return Response.build(board);
    }
}
