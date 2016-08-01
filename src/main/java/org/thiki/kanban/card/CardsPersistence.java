package org.thiki.kanban.card;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsPersistence {

    void create(Card card);

    Card findById(String cardId);

    void update(@Param("id") String cardId, @Param("card") Card card);

    List<Card> findByEntryId(String entryId);

    Integer deleteById(@Param("id") String id);

    Integer resortTargetEntry(@Param("cardId") String cardId, @Param("currentEntryId") String currentEntryId, @Param("currentOrderNumber") Integer currentOrderNumber);

    Integer resortOriginEntry(@Param("cardId") String cardId, @Param("originEntryId") String originEntryId, @Param("originOrderNumber") Integer originOrderNumber);

    Integer resortOrder(@Param("cardId") String cardId, @Param("entryId") String entryId, @Param("originOrderNumber") Integer originOrderNumber, @Param("currentOrderNumber") Integer currentOrderNumber, @Param("increment") int increment);
}
