package org.thiki.kanban.risk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cain on 2017/2/28.
 */
@Service
public class RiskService {

    @Autowired
    RiskPersistence riskPersistence;

    public static Logger logger = LoggerFactory.getLogger(RiskService.class);

    public Risk addRisk(String userName, String cardId, Risk risk) {
        riskPersistence.addRisk(userName, cardId, risk);
        Risk savedRisk = riskPersistence.findRiskById(risk.getId());
        logger.info("Saved risk:{}", savedRisk);
        return savedRisk;
    }

    public Risk loadRiskById(String riskId) {
        return riskPersistence.findRiskById(riskId);
    }
}
