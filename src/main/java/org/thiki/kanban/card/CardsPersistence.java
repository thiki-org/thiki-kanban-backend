package org.thiki.kanban.card;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsPersistence {

    void create(@Param("userName") String userName, @Param("card") Card card);

    Card findById(@Param("cardId") String cardId);

    Integer modify(@Param("cardId") String cardId, @Param("card") Card card);

    List<Card> findByProcedureId(@Param("procedureId") String procedureId);

    Integer deleteById(@Param("cardId") String cardId);

    Integer resort(Card card);

    int totalCardsIncludingDeleted(@Param("boardId") String boardId, @Param("currentMonth") String currentMonth);

    List<Card> loadUnArchivedCards();
}
