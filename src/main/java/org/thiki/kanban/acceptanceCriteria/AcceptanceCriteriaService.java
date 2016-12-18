package org.thiki.kanban.acceptanceCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public AcceptanceCriteria addAcceptCriteria(String userName, String cardId, AcceptanceCriteria acceptanceCriteria) {
        logger.info("User {} adds a acceptance criteria in card [{}],the acceptanceCriteria is:{}", userName, cardId, acceptanceCriteria);
        acceptanceCriteriaPersistence.addAcceptCriteria(userName, cardId, acceptanceCriteria);
        return acceptanceCriteriaPersistence.findById(acceptanceCriteria.getId());
    }

    public List<AcceptanceCriteria> loadAcceptanceCriteriasByCardId(String cardId) {
        List<AcceptanceCriteria> acceptanceCriterias = acceptanceCriteriaPersistence.loadAcceptanceCriteriasByCardId(cardId);
        return acceptanceCriterias;
    }

    public AcceptanceCriteria loadAcceptanceCriteriaById(String acceptanceCriteriaId) {
        return acceptanceCriteriaPersistence.findById(acceptanceCriteriaId);
    }

    public AcceptanceCriteria updateAcceptCriteria(String acceptanceCriteriaId, AcceptanceCriteria acceptanceCriteria) {
        acceptanceCriteriaPersistence.updateAcceptCriteria(acceptanceCriteriaId, acceptanceCriteria);
        return acceptanceCriteriaPersistence.findById(acceptanceCriteriaId);
    }

    public Integer removeAcceptanceCriteria(String acceptanceCriteriaId) {
        return acceptanceCriteriaPersistence.deleteAcceptanceCriteria(acceptanceCriteriaId);
    }

    public List<AcceptanceCriteria> resortAcceptCriterias(String cardId, List<AcceptanceCriteria> acceptanceCriterias) {
        for (AcceptanceCriteria acceptanceCriteria : acceptanceCriterias) {
            acceptanceCriteriaPersistence.resort(acceptanceCriteria);
        }
        return acceptanceCriteriaPersistence.loadAcceptanceCriteriasByCardId(cardId);
    }
}
