package org.thiki.kanban.tag;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubt on 11/7/16.
 */
@Repository
public interface TagPersistence {
    Integer addTag(@Param("userName") String userName, @Param("tag") Tag tag);

    Tag findById(String id);

    List<Tag> loadTagsByUserName(@Param("userName") String userName);

    Integer deleteAcceptanceCriteria(String acceptanceCriteriaId);

    Integer resort(@Param("tag") Tag tag);

    Integer updateTag(@Param("tagId") String tagId, @Param("tag") Tag tag);
}
