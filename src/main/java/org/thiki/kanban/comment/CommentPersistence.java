package org.thiki.kanban.comment;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubt on 10/31/16.
 */
@Repository
public interface CommentPersistence {
    Integer addComment(@Param("userName") String userName, @Param("cardId") String cardId, @Param("comment") Comment comment);

    Comment findById(String id);

    List<Comment> loadCommentsByCardId(@Param("cardId") String cardId);

    Integer deleteComment(String acceptanceCriteriaId);
}
