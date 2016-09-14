package org.thiki.kanban.user.registration;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.SequenceNumber;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.user.User;
import org.thiki.kanban.user.UsersService;

import javax.annotation.Resource;

/**
 * Created by joeaniu on 6/21/16.
 */
@Service
public class RegistrationService {

    @Resource
    private RegistrationPersistence registrationPersistence;
    @Resource
    private UsersService usersService;

    @Resource
    private SequenceNumber sequenceNumber;

    @Resource
    private RSAService rsaService;

    public User register(Registration registration) throws Exception {
        boolean isNameExist = usersService.isNameExist(registration.getName());
        if (isNameExist) {
            throw new BusinessException(UsersCodes.USERNAME_IS_ALREADY_EXISTS);
        }

        boolean isEmailExist = usersService.isEmailExist(registration.getEmail());
        if (isEmailExist) {
            throw new BusinessException(UsersCodes.EMAIL_IS_ALREADY_EXISTS);
        }

        registration.setSalt(sequenceNumber.generate());

        String password = rsaService.dencrypt(registration.getPassword());
        password = MD5Service.encrypt(password + registration.getSalt());
        if (password != null) {
            registration.setPassword(password);
        }
        registrationPersistence.register(registration);
        return usersService.findByName(registration.getName());
    }
}
