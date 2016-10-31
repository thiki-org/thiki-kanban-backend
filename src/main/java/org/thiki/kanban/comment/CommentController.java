package org.thiki.kanban.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

/**
 * Created by xubt on 10/31/16.
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/comments", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Comment comment, @RequestHeader String userName, @PathVariable("cardId") String cardId, @PathVariable("procedureId") String procedureId) {
        Comment savedComment = commentService.addAcceptCriteria(userName, cardId, comment);

        return Response.post(new CommentResource(savedComment, cardId, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/comments/{commentId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable("cardId") String cardId, @PathVariable("commentId") String commentId, @PathVariable("procedureId") String procedureId) {
        Comment savedComment = commentService.loadCommentById(commentId);

        return Response.build(new CommentResource(savedComment, cardId, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/comments/{commentId}", method = RequestMethod.DELETE)
    public HttpEntity removeComment(@PathVariable("cardId") String cardId, @PathVariable("commentId") String commentId, @PathVariable("procedureId") String procedureId) {
        commentService.removeComment(commentId);

        return Response.build(new CommentResource(cardId, procedureId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/comments", method = RequestMethod.GET)
    public HttpEntity loadCommentsByCardId(@PathVariable("cardId") String cardId, @PathVariable("procedureId") String procedureId) {
        List<Comment> commentList = commentService.loadCommentsByCardId(cardId);
        return Response.build(new CommentsResource(commentList, cardId, procedureId));
    }
}
