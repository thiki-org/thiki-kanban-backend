package org.thiki.kanban.comment;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 10/31/16.
 */
@Service
public class CommentService {

    @Resource
    private CommentPersistence commentPersistence;

    public Comment addAcceptCriteria(String userName, String cardId, Comment comment) {

        commentPersistence.addComment(userName, cardId, comment);
        return commentPersistence.findById(comment.getId());
    }

    public List<Comment> loadCommentsByCardId(String cardId) {
        List<Comment> comments = commentPersistence.loadCommentsByCardId(cardId);
        return comments;
    }

    public Comment loadCommentById(String commentId) {
        return commentPersistence.findById(commentId);
    }

    public Integer removeComment(String commentId) {
        return commentPersistence.deleteComment(commentId);
    }
}
