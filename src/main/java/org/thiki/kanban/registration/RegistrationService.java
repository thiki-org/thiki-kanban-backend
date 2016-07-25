package org.thiki.kanban.registration;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.SequenceNumber;
import org.thiki.kanban.foundation.exception.ExceptionCode;
import org.thiki.kanban.foundation.exception.ResourceConflictException;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.user.UsersPersistence;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * Created by joeaniu on 6/21/16.
 */
@Service
public class RegistrationService {

    @Resource
    RegistrationPersistence registrationPersistence;

    @Resource
    UsersPersistence usersPersistence;

    @Resource
    private SequenceNumber sequenceNumber;

    @Resource
    private RSAService rsaService;

    public Registration findByName(String userName) {
        return registrationPersistence.findByName(userName);
    }

    public Registration register(Registration registration) throws Exception {
        Registration conflictNameUser = registrationPersistence.findByName(registration.getName());
        if (conflictNameUser != null) {
            throw new ResourceConflictException(ExceptionCode.USER_EXISTS.code(), MessageFormat.format("User named {0} is already exists.", registration.getName()));
        }

        Registration conflictEmailUser = registrationPersistence.findByEmail(registration.getEmail());
        if (conflictEmailUser != null) {
            throw new ResourceConflictException(ExceptionCode.USER_EXISTS.code(), MessageFormat.format("Email {0} is already exists.", registration.getEmail()));
        }

        registration.setSalt(sequenceNumber.generate());

        String password = rsaService.dencrypt(registration.getPassword());
        password = MD5Service.encrypt(password + registration.getSalt());
        if (password != null) {
            registration.setPassword(password);
        }
        registrationPersistence.create(registration);
        return findByName(registration.getName());
    }
}
