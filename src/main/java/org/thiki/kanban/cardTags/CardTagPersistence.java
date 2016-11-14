package org.thiki.kanban.cardTags;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubt on 11/14/16.
 */
@Repository
public interface CardTagPersistence {

    Integer stick(@Param("cardId") String cardId, @Param("cardTag") CardTag cardTag, @Param("userName") String userName);

    List<CardTag> findByCardId(String cardId);
}
