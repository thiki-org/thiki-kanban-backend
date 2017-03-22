package org.thiki.kanban.risk;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cain on 2017/2/28.
 */
@Repository
public interface RiskPersistence {

    Integer addRisk(@Param("userName") String userName, @Param("cardId") String cardId, @Param("risk") Risk risk);

    Risk findRiskById(String id);

    List<Risk> findCardRisks(@Param("cardId") String cardId);

    Integer deleteRisk(String id);
}
