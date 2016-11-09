package org.thiki.kanban.tag;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubt on 11/7/16.
 */
@Repository
public interface TagPersistence {
    Integer addTag(@Param("boardId") String boardId, @Param("tag") Tag tag);

    Tag findById(String id);

    List<Tag> loadTagsByBoard(@Param("boardId") String boardId);

    Integer resort(@Param("tag") Tag tag);

    Integer updateTag(@Param("tagId") String tagId, @Param("tag") Tag tag);

    Integer deleteTag(String tagId);

    boolean isNameDuplicate(@Param("boardId") String boardId, @Param("tag") Tag tag);

    boolean isColorDuplicate(@Param("boardId") String boardId, @Param("tag") Tag tag);

    void cloneTagsFromOtherBoard(@Param("boardId") String boardId, @Param("fromBoardId") String fromBoardId);
}
