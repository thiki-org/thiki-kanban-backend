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

    @RequestMapping(value = "/boards", method = RequestMethod.GET)
    public HttpEntity<BoardsResource> loadAll() {
        List<Board> boards = boardsService.loadAll();
        return Response.build(new BoardsResource(boards));
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id) {
        Board board = boardsService.findById(id);
        return Response.build(new BoardResource(board));
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Board board, @PathVariable String id) {
        board.setId(id);
        Board updatedBoard = boardsService.update(board);
        return Response.build(new BoardResource(updatedBoard));

    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id) {
        boardsService.deleteById(id);
        return Response.build(new BoardResource());
    }

    @RequestMapping(value = "/boards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Board board, @RequestHeader Integer userId) {
        Board savedBoard = boardsService.create(userId, board);
        return Response.post(new BoardResource(savedBoard));
    }

    @RequestMapping(value = "/users/{userId}/boards", method = RequestMethod.GET)
    public HttpEntity<BoardsResource> findByUserId(@PathVariable String userId) {
        List<Board> boards = boardsService.findByUserId(userId);
        return Response.build(new BoardsResource(boards));
    }
    @RequestMapping(value = "/teams/{teamId}/boards", method = RequestMethod.GET)
    public HttpEntity<BoardsResource> findByTeamId(@PathVariable String teamId) {
        List<Board> boards = boardsService.findByTeamId(teamId);
        return Response.build(new BoardsResource(boards));
    }
}
