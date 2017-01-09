package org.thiki.kanban.procedure;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface ProceduresPersistence {
    Integer create(@Param("procedure") Procedure procedure, @Param("userName") String userName, @Param("boardId") String boardId);

    Procedure findById(@Param("procedureId") String procedureId);

    List<Procedure> loadByBoardId(String boardId);

    Integer update(@Param("procedureId") String procedureId, @Param("procedure") Procedure procedure);

    Integer deleteById(@Param("procedureId") String procedureId);

    Integer resort(Procedure procedure);

    boolean uniqueTitle(@Param("boardId") String boardId, @Param("title") String title);

    boolean isDoneProcedureAlreadyExist(@Param("procedureId") String procedureId, @Param("boardId") String boardId);

    Procedure findDoneProcedure(@Param("boardId") String boardId);
}
