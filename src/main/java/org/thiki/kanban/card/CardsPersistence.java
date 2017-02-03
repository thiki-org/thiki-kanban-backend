package org.thiki.kanban.card;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsPersistence {

    void create(@Param("userName") String userName, @Param("card") Card card);

    Card findById(String cardId);

    void modify(@Param("id") String cardId, @Param("card") Card card);

    List<Card> findByProcedureId(String procedureId);

    Integer deleteById(@Param("id") String id);

    Integer resort(Card card);

    boolean isCodeAlreadyExist(@Param("cardId") String cardId, @Param("code") String code, @Param("procedureId") String procedureId);

    int totalCardsIncludingDeleted(String procedureId);
}
