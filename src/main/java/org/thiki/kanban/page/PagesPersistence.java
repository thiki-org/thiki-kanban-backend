package org.thiki.kanban.page;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagesPersistence {

    Integer addPage(@Param("page") Page page, @Param("boardId") String boardId, @Param("userName") String userName);

    Page findById(@Param("pageId") String pageId, @Param("boardId") String boardId);

    Integer modify(@Param("pageId") String pageId, @Param("boardId") String boardId, @Param("page") Page page);

    List<Page> findByBoardId(String boardId);

    Integer deleteById(@Param("id") String id);

    Integer removePage(@Param("pageId") String pageId, @Param("boardId") String boardId);
}
