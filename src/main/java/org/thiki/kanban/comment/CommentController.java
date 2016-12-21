package org.thiki.kanban.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 10/31/16.
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Resource
    private CommentResource commentResource;
    @Resource
    private CommentsResource commentsResource;

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/comments", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Comment comment, @RequestHeader String userName, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId) throws Exception {
        Comment savedComment = commentService.addComment(userName, cardId, comment);

        return Response.post(commentResource.toResource(savedComment, boardId, procedureId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/comments/{commentId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @PathVariable String commentId, @RequestHeader String userName) throws Exception {
        Comment savedComment = commentService.loadCommentById(commentId);

        return Response.build(commentResource.toResource(savedComment, boardId, procedureId, cardId, userName));
    }

    @RequestMapping(value = CommentResource.URL_TEMPLATE, method = RequestMethod.DELETE)
    public HttpEntity removeComment(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @PathVariable String commentId, @RequestHeader String userName) throws Exception {
        commentService.removeComment(commentId);

        return Response.build(commentResource.toResource(boardId, procedureId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/comments", method = RequestMethod.GET)
    public HttpEntity loadCommentsByCardId(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        List<Comment> commentList = commentService.loadCommentsByCardId(cardId);
        return Response.build(commentsResource.toResource(commentList, boardId, procedureId, cardId, userName));
    }
}
