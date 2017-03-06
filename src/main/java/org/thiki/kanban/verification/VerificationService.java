package org.thiki.kanban.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by skytao on 03/06/17.
 */
@Service
public class VerificationService {
    public static Logger logger = LoggerFactory.getLogger(VerificationService.class);

    @Resource
    private VerificationPersistence verificationPersistence;
    @Resource
    private AcceptanceCriteriaService acceptanceCriteriaService;
    @Resource
    private CardsService cardsService;

    @CacheEvict(value = "verification", key = "contains('#acceptanceCriteriaId')", allEntries = true)
    public List<Verification> addVerification(Verification verification, String acceptanceCriteriaId, String userName) {
        logger.info("Verify acceptance criterias,verification:{},acceptanceCriteriaId:{},userName:{}", verification, acceptanceCriteriaId, userName);
        Optional<AcceptanceCriteria> acceptanceCriteria = Optional.ofNullable(acceptanceCriteriaService.loadAcceptanceCriteriaById(acceptanceCriteriaId));
        if (!acceptanceCriteria.isPresent()) {
            throw new BusinessException(VerificationCodes.ACCEPTANCE_CRITERIA_IS_NOT_FOUND);
        }
        if (!acceptanceCriteria.get().getFinished()) {
            throw new BusinessException(VerificationCodes.ACCEPTANCE_CRITERIA_IS_NOT_FINISHED);
        }
        boolean isCardArchivedOrDone = cardsService.isCardArchivedOrDone(acceptanceCriteria.get().getCardId());
        if (isCardArchivedOrDone) {
            throw new BusinessException(VerificationCodes.CARD_HAS_ALREADY_BEEN_ARCHIVED_OR_DONE);
        }
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
