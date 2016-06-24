package org.thiki.kanban.entry;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface EntriesPersistence {
    Integer create(Entry entry);

    Entry findById(@Param("id") String id);

    List<Entry> loadByBoardId(String boardId);

    Integer update(Entry entry);

    Integer deleteById(@Param("id") String id);

    Boolean checkRedundancy(Entry entry);

    void resort(Map<String, Object> entry);
}
