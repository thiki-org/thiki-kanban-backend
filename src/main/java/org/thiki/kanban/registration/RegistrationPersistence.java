package org.thiki.kanban.registration;


import org.springframework.stereotype.Repository;

/**
 * Created by joeaniu on 6/21/16.
 */
@Repository
public interface RegistrationPersistence {
    void register(Registration userRegistration);
}
