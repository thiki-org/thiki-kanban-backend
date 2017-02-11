package org.thiki.kanban.page;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagesPersistence {

    void addPage(@Param("page") Page page, @Param("boardId") String boardId, @Param("userName") String userName);

    Page findById(@Param("pageId") String pageId);

    void modify(@Param("id") String pageId, @Param("page") Page page);

    List<Page> findByBoardId(String boardId);

    Integer deleteById(@Param("id") String id);
}
