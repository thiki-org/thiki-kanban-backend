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

    @RequestMapping(value = "/{userName}/boards/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @PathVariable String userName) {
        Board board = boardsService.findById(id);
        return Response.build(new BoardResource(board, userName));
    }

    @RequestMapping(value = "/{userName}/boards/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Board board, @PathVariable String id, @PathVariable String userName) {
        board.setId(id);
        Board updatedBoard = boardsService.update(userName, board);
        return Response.build(new BoardResource(updatedBoard, userName));

    }

    @RequestMapping(value = "/{userName}/boards/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id, @PathVariable String userName) {
        boardsService.deleteById(id);
        return Response.build(new BoardResource(userName));
    }

    @RequestMapping(value = "/{userName}/boards", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Board board, @PathVariable String userName) {
        Board savedBoard = boardsService.create(userName, board);
        return Response.post(new BoardResource(savedBoard, userName));
    }

    @RequestMapping(value = "/{userName}/boards", method = RequestMethod.GET)
    public HttpEntity loadByUserName(@PathVariable String userName) {
        List<Board> boards = boardsService.loadByUserName(userName);
        return Response.build(new BoardsResource(boards));
    }
}
