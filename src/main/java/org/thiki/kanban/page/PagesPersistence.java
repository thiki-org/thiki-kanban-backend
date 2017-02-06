package org.thiki.kanban.page;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagesPersistence {

    void create(@Param("userName") String userName, @Param("page") Page page);

    Page findById(String pageId);

    void modify(@Param("id") String pageId, @Param("page") Page page);

    List<Page> findByBoardId(String boardId);

    Integer deleteById(@Param("id") String id);

}
