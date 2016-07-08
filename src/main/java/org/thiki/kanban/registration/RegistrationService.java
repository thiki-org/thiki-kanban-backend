package org.thiki.kanban.registration;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.Sequence;
import org.thiki.kanban.foundation.exception.ExceptionCode;
import org.thiki.kanban.foundation.exception.ResourceConflictException;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAUtils;
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
    private Sequence sequence;

    /**
     * 根据用户名查找用户嘻嘻
     *
     * @param userName
     */
    public Registration findByName(String userName) {
        return registrationPersistence.findByName(userName);
    }

    public Registration register(Registration registration) {
        Registration conflictNameUser = registrationPersistence.findByName(registration.getName());
        if (conflictNameUser != null) {
            throw new ResourceConflictException(ExceptionCode.USER_EXISTS.code(), MessageFormat.format("用户名[{0}]已经存在.", registration.getName()));
        }

        Registration conflictEmailUser = registrationPersistence.findByEmail(registration.getEmail());
        if (conflictEmailUser != null) {
            throw new ResourceConflictException(ExceptionCode.USER_EXISTS.code(), MessageFormat.format("邮箱[{0}]已经存在.", registration.getEmail()));
        }

        registration.setSalt(sequence.generate());

        String password = RSAUtils.decrypt(registration.getPassword());
        password = MD5Service.encrypt(password + registration.getSalt());
        if (password != null) {
            registration.setPassword(password);
        }
        registrationPersistence.create(registration);
        return findByName(registration.getName());
    }
}
