package org.thiki.kanban.comment;

import org.springframework.cache.annotation.CacheEvict;
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

    @CacheEvict(value = "comment", key = "contains('#cardId')", allEntries = true)
    public Comment addComment(String userName, String cardId, Comment comment) {
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

    @CacheEvict(value = "comment", key = "contains('#commentId')", allEntries = true)
    public Integer removeComment(String commentId) {
        return commentPersistence.deleteComment(commentId);
    }
}
