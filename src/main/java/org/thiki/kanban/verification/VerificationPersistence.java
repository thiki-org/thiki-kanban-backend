package org.thiki.kanban.verification;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by skytao on 03/06/17.
 */
@Repository
public interface VerificationPersistence {
    Integer addVerification(@Param("verification") Verification verification, @Param("acceptanceCriteriaId") String acceptanceCriteriaId, @Param("userName") String userName);

    List<Verification> loadVerificationsByAcceptanceCriteria(@Param("acceptanceCriteriaId") String acceptanceCriteriaId);
}
