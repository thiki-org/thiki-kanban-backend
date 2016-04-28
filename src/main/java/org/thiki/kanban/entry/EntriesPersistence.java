package org.thiki.kanban.entry;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface EntriesPersistence {
    Integer create(Entry entry);

    Entry findById(@Param("id") Integer id);

    List<Entry> loadAll();

    Integer update(Entry entry);

    Integer deleteById(@Param("id") Integer id);

    Boolean checkRedundancy(Entry entry);
}
