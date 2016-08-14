package org.thiki.kanban.passwordRetrieval;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.team.Team;

import javax.annotation.Resource;

/**
 * Created by xubt on 8/8/16.
 */
@Service
public class PasswordRetrievalService {
    @Resource
    private PasswordRetrievalPersistence passwordRetrievalPersistence;

    public Team createRetrievalRecord(PasswordRetrieval passwordRetrieval) {

        boolean isFoundEmail = passwordRetrievalPersistence.existsEmail(passwordRetrieval.getEmail());
        if (!isFoundEmail) {
            throw new BusinessException(PasswordRetrievalCodes.EmailIsNotExists.code(), PasswordRetrievalCodes.EmailIsNotExists.message());
        }
        return null;
    }
}
