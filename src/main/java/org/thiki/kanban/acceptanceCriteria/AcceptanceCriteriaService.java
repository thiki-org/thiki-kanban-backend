package org.thiki.kanban.acceptanceCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.foundation.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 10/17/16.
 */
@Service
public class AcceptanceCriteriaService {
    public static Logger logger = LoggerFactory.getLogger(AcceptanceCriteriaService.class);

    @Resource
    private AcceptanceCriteriaPersistence acceptanceCriteriaPersistence;

    @Resource
    private ActivityService activityService;

    @CacheEvict(value = "acceptanceCriteria", key = "contains('#cardId')", allEntries = true)
    public AcceptanceCriteria addAcceptCriteria(String userName, String cardId, AcceptanceCriteria acceptanceCriteria) {
        logger.info("User {} adds a acceptance criteria in card [{}],the acceptanceCriteria is:{}", userName, cardId, acceptanceCriteria);
        acceptanceCriteriaPersistence.addAcceptCriteria(userName, cardId, acceptanceCriteria);
        AcceptanceCriteria savedAcceptanceCriteria = acceptanceCriteriaPersistence.findById(acceptanceCriteria.getId());
        logger.info("Saved acceptanceCriteria:{}", savedAcceptanceCriteria);
        activityService.recordAcceptanceCriteriaCreation(acceptanceCriteria, cardId, userName);
        return savedAcceptanceCriteria;
    }

    @Cacheable(value = "acceptanceCriteria", key = "'acceptanceCriterias'+#cardId")
    public List<AcceptanceCriteria> loadAcceptanceCriteriasByCardId(String cardId) {
        logger.info("Loading acceptanceCriterias by cardId.cardId:{}", cardId);
        List<AcceptanceCriteria> acceptanceCriterias = acceptanceCriteriaPersistence.loadAcceptanceCriteriasByCardId(cardId);
        logger.info("Loaded acceptanceCriterias:{}", acceptanceCriterias);
        return acceptanceCriterias;
    }

    @Cacheable(value = "acceptanceCriteria", key = "'acceptanceCriterias'+#acceptanceCriteriaId")
    public AcceptanceCriteria loadAcceptanceCriteriaById(String acceptanceCriteriaId) {
        logger.info("Loading acceptanceCriteria by Id:{}", acceptanceCriteriaId);
        AcceptanceCriteria acceptanceCriteria = acceptanceCriteriaPersistence.findById(acceptanceCriteriaId);
        logger.info("Loaded acceptanceCriteria:{}", acceptanceCriteria);
        return acceptanceCriteria;
    }

    @CacheEvict(value = "acceptanceCriteria", key = "contains('#cardId')", allEntries = true)
    public AcceptanceCriteria updateAcceptCriteria(String cardId, String acceptanceCriteriaId, AcceptanceCriteria acceptanceCriteria, String userName) {
        logger.info("Update acceptanceCriteria.acceptanceCriteriaId:{},acceptanceCriteria:{},cardId:{}", acceptanceCriteriaId, acceptanceCriteria, cardId);
        acceptanceCriteriaPersistence.updateAcceptCriteria(acceptanceCriteriaId, acceptanceCriteria);
        AcceptanceCriteria updatedAcceptanceCriteria = acceptanceCriteriaPersistence.findById(acceptanceCriteriaId);
        logger.info("Updated acceptanceCriteria:{}", updatedAcceptanceCriteria);

        boolean isHasUnFinishedAcceptanceCriterias = acceptanceCriteriaPersistence.isHasUnFinishedAcceptanceCriterias(cardId);
        activityService.recordAcceptanceCriteriaModification(acceptanceCriteria, updatedAcceptanceCriteria, cardId, isHasUnFinishedAcceptanceCriterias, userName);

        return updatedAcceptanceCriteria;
    }

    @CacheEvict(value = "acceptanceCriteria", key = "contains('#cardId')", allEntries = true)
    public Integer removeAcceptanceCriteria(String acceptanceCriteriaId, String cardId, String userName) {
        logger.info("Remove acceptanceCriteria.acceptanceCriteriaId:{},cardId:{}", acceptanceCriteriaId, cardId);
        AcceptanceCriteria acceptanceCriteria = acceptanceCriteriaPersistence.findById(acceptanceCriteriaId);
        if (acceptanceCriteria == null) {
            throw new BusinessException(AcceptanceCriteriaCodes.ACCEPTANCE_CRITERIA_IS_NOT_FOUND);
        }
        activityService.recordAcceptanceCriteriaRemoving(acceptanceCriteriaId, acceptanceCriteria, cardId, userName);
        return acceptanceCriteriaPersistence.deleteAcceptanceCriteria(acceptanceCriteriaId);
    }

    @CacheEvict(value = "acceptanceCriteria", key = "contains('#cardId')", allEntries = true)
    public List<AcceptanceCriteria> resortAcceptCriterias(String cardId, List<AcceptanceCriteria> acceptanceCriterias, String userName) {
        logger.info("Resorting acceptanceCriterias.acceptanceCriterias:{},cardId:{}", acceptanceCriterias, cardId);
        for (AcceptanceCriteria acceptanceCriteria : acceptanceCriterias) {
            acceptanceCriteriaPersistence.resort(acceptanceCriteria);
        }
        List<AcceptanceCriteria> resortedAcceptanceCriterias = acceptanceCriteriaPersistence.loadAcceptanceCriteriasByCardId(cardId);
        logger.info("Resorted acceptanceCriterias:{}", resortedAcceptanceCriterias);
        activityService.recordAcceptanceCriteriaResorting(acceptanceCriterias, resortedAcceptanceCriterias, cardId, userName);
        return resortedAcceptanceCriterias;
    }

    public boolean isAllAcceptanceCriteriasCompleted(String cardId) {
        return !acceptanceCriteriaPersistence.isHasUnFinishedAcceptanceCriterias(cardId);
    }
}
