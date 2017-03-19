package org.thiki.kanban.risk;

import com.sun.org.apache.bcel.internal.generic.VariableLengthInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cain on 2017/2/28.
 */
@Service
public class RiskService {

    @Autowired
    RiskPersistence riskPersistence;

    public static Logger logger = LoggerFactory.getLogger(RiskService.class);

    @CacheEvict(value = "risk", key = "contains('#cardId')", allEntries = true)
    public Risk addRisk(String userName, String cardId, Risk risk) {
        risk.setRiskResolved(false);
        riskPersistence.addRisk(userName, cardId, risk);
        Risk savedRisk = riskPersistence.findRiskById(risk.getId());
        logger.info("Saved risk:{}", savedRisk);
        return savedRisk;
    }

    public Risk loadRiskById(String riskId) {
        return riskPersistence.findRiskById(riskId);
    }

    @Cacheable(value = "risk", key = "'risk'+#cardId")
    public List<Risk> loadCardRisks(String cardId) {
        return riskPersistence.findCardRisks(cardId);
    }

    @CacheEvict(value = "risk", key = "contains('#cardId')", allEntries = true)
    public Integer removeRisk(String riskId) {
        return riskPersistence.deleteRisk(riskId);
    }
}
