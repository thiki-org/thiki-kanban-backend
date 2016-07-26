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

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @RequestHeader String userName) {
        Board board = boardsService.findById(id);
        return Response.build(new BoardResource(board, userName));
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Board board, @PathVariable String id, @RequestHeader String userName) {
        board.setId(id);
        Board updatedBoard = boardsService.update(board);
        return Response.build(new BoardResource(updatedBoard, userName));

    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id, @RequestHeader String userName) {
        boardsService.deleteById(id);
        return Response.build(new BoardResource(userName));
    }

    @RequestMapping(value = "/boards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Board board, @RequestHeader String userName) {
        Board savedBoard = boardsService.create(userName, board);
        return Response.post(new BoardResource(savedBoard, userName));
    }

    @RequestMapping(value = "/boards", method = RequestMethod.GET)
    public HttpEntity loadByUserName(@RequestHeader String userName) {
        List<Board> boards = boardsService.findByUserId(userName);
        return Response.build(new BoardsResource(boards));
    }

    @RequestMapping(value = "/teams/{teamId}/boards", method = RequestMethod.GET)
    public HttpEntity findByTeamId(@PathVariable String teamId) {
        List<Board> boards = boardsService.findByTeamId(teamId);
        return Response.build(new BoardsResource(boards));
    }
}
