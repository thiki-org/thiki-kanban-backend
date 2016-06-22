package org.thiki.kanban.registration;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by joeaniu on 6/21/16.
 */
@Service
public class RegistrationService {

    @Resource
    RegistractionPersistence registractionPersistence;

    public UserRegistration registerNewUser(String name, String mail, String mobile, String passwd, String captcha) {

        return null;
    }
}
