package org.thiki.kanban.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.activity.ActivityService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 10/31/16.
 */
@Service
public class CommentService {
    public static Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Resource
    private CommentPersistence commentPersistence;

    @Resource
    private ActivityService activityService;

    @CacheEvict(value = "comment", key = "contains('#cardId')", allEntries = true)
    public Comment addComment(String userName, String cardId, Comment comment) {
        logger.info("Add comment.userName:{},cardId:{},comment:{}", userName, cardId, comment);
        commentPersistence.addComment(userName, cardId, comment);
        Comment savedComment = commentPersistence.findById(comment.getId());
        logger.info("Saved comment:{}", savedComment);
        activityService.recordComment(comment, cardId, userName);
        return savedComment;
    }

    @Cacheable(value = "comment", key = "'comments'+#cardId")
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
