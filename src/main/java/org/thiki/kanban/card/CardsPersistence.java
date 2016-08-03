package org.thiki.kanban.card;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsPersistence {

    void create(Card card);

    Card findById(String cardId);

    void update(@Param("id") String cardId, @Param("card") Card card);

    List<Card> findByProcedureId(String procedureId);

    Integer deleteById(@Param("id") String id);

    Integer resortTargetProcedure(@Param("cardId") String cardId, @Param("currentProcedureId") String currentProcedureId, @Param("currentOrderNumber") Integer currentOrderNumber);

    Integer resortOriginProcedure(@Param("cardId") String cardId, @Param("originProcedureId") String originProcedureId, @Param("originOrderNumber") Integer originOrderNumber);

    Integer resortOrder(@Param("cardId") String cardId, @Param("procedureId") String procedureId, @Param("originOrderNumber") Integer originOrderNumber, @Param("currentOrderNumber") Integer currentOrderNumber, @Param("increment") int increment);
}
