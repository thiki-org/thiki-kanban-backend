package org.thiki.kanban.registration;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by joeaniu on 6/21/16.
 */
@Repository
public interface RegistrationPersistence {

    void create(Registration userRegistration);

    Registration findByName(String userName);

    boolean existsUser(Registration registration);

    Registration findByEmail(String email);

    Registration findByIdentity(@Param("identity") String identity, @Param("password") String password);
}
