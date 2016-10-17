package org.thiki.kanban.acceptanceCriteria;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xubt on 10/17/16.
 */
@Service
public class AcceptCriteriaService {

    @Resource
    private AcceptCriteriaPersistence acceptCriteriaPersistence;

    public AcceptCriteria addAcceptCriteria(String userName, String cardId, AcceptCriteria acceptCriteria) {

        acceptCriteriaPersistence.addAcceptCriteria(userName, cardId, acceptCriteria);
        return acceptCriteriaPersistence.findById(acceptCriteria.getId());
    }
}
