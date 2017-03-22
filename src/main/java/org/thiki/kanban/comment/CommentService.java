package org.thiki.kanban.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaCodes;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;

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
    @Resource
    private CardsService cardsService;

    @CacheEvict(value = "comment", key = "contains('#cardId')", allEntries = true)
    public Comment addComment(String userName, String cardId, Comment comment) {
        logger.info("Add comment.userName:{},cardId:{},comment:{}", userName, cardId, comment);
        boolean isCardArchivedOrDone = cardsService.isCardArchivedOrDone(cardId);
        if (isCardArchivedOrDone) {
            throw new BusinessException(AcceptanceCriteriaCodes.CARD_WAS_ALREADY_DONE_OR_ARCHIVED);
        }
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
