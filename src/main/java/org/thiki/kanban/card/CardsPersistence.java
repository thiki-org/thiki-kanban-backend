package org.thiki.kanban.card;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsPersistence {

    void create(@Param("userName") String userName, @Param("card") Card card);

    Card findById(@Param("cardId") String cardId);

    Integer modify(@Param("cardId") String cardId, @Param("card") Card card);

    List<Card> findByStageId(@Param("stageId") String stageId);

    Integer deleteById(@Param("cardId") String cardId);

    Integer move(Card card);

    int totalCardsIncludingDeleted(@Param("boardId") String boardId, @Param("currentMonth") String currentMonth);

    List<Card> loadUnArchivedCards();

    boolean hasChild(String cardId);

    List<Card> loadChildCards(String cardId);
}
