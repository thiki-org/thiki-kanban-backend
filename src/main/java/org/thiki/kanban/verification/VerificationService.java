package org.thiki.kanban.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by skytao on 03/06/17.
 */
@Service
public class VerificationService {
    public static Logger logger = LoggerFactory.getLogger(VerificationService.class);

    @Resource
    private VerificationPersistence verificationPersistence;

    @CacheEvict(value = "verification", key = "contains('#acceptanceCriteriaId')", allEntries = true)
    public List<Verification> addVerification(Verification verification, String acceptanceCriteriaId, String userName) {
        logger.info("Verify acceptance criterias,verification:{},acceptanceCriteriaId:{},userName:{}", verification, acceptanceCriteriaId, userName);
        verificationPersistence.addVerification(verification, acceptanceCriteriaId, userName);
        return loadVerificationsByAcceptanceCriteria(acceptanceCriteriaId);
    }

    @Cacheable(value = "acceptanceCriteria", key = "'acceptanceCriterias'+#cardId")
    public List<Verification> loadVerificationsByAcceptanceCriteria(String cardId) {
        logger.info("Loading verifications by cardId.cardId:{}", cardId);
        List<Verification> verifications = verificationPersistence.loadVerificationsByAcceptanceCriteria(cardId);
        logger.info("Loaded verifications:{}", verifications);
        return verifications;
    }
}
