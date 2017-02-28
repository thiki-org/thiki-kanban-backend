package org.thiki.kanban.board;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 05/26/16.
 */
@RestController
public class BoardsController {
    @Resource
    private BoardsService boardsService;
    @Resource
    private BoardResource boardResource;
    @Resource
    private BoardsResource boardsResource;

    @RequestMapping(value = "/{userName}/projects/{projectId}/boards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Board board, @PathVariable String projectId, @PathVariable String userName) throws Exception {
        Board savedBoard = boardsService.create(userName, board, projectId);
        return Response.post(boardResource.toResource(savedBoard, projectId, userName));
    }

    @RequestMapping(value = "/{userName}/projects/{projectId}/boards/{boardId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String projectId, @PathVariable String userName) throws Exception {
        Board board = boardsService.findById(boardId);
        return Response.build(boardResource.toResource(board, projectId, userName));
    }

    @RequestMapping(value = "/{userName}/projects/{projectId}/boards/{boardId}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Board board, @PathVariable String boardId, @PathVariable String projectId, @PathVariable String userName) throws Exception {
        board.setId(boardId);
        Board updatedBoard = boardsService.update(userName, projectId, board);
        return Response.build(boardResource.toResource(updatedBoard, projectId, userName));

    }

    @RequestMapping(value = "/{userName}/projects/{projectId}/boards/{boardId}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String boardId, @PathVariable String projectId, @PathVariable String userName) throws Exception {
        boardsService.deleteById(boardId, userName);
        return Response.build(boardResource.toResource(projectId, userName));
    }

    @RequestMapping(value = "/{userName}/projects/{projectId}/boards", method = RequestMethod.GET)
    public HttpEntity loadByProject(@PathVariable String projectId, @PathVariable String userName) throws Exception {
        List<Board> boards = boardsService.loadBoards(projectId, userName);
        return Response.build(boardsResource.toResource(boards, projectId, userName));
    }
}
