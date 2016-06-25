package org.thiki.kanban.registration;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.ExceptionCode;
import org.thiki.kanban.foundation.exception.ResourceExistException;
import org.thiki.kanban.user.UserProfile;
import org.thiki.kanban.user.UsersPersistence;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joeaniu on 6/21/16.
 */
@Service
public class RegistrationService {

    @Resource
    RegistrationPersistence registrationPersistence;

    @Resource
    UsersPersistence usersPersistence;

    public Map<String, Object> registerNewUser(String userName, String email, String phone, String passwd, String captcha) {

        if (!isCaptchaValid(captcha)){
            throw new BusinessException(ExceptionCode.INVALID_PARAMS.code(), "captcha is invalid!");
        }

        boolean existsUser = usersPersistence.existsUser(userName, email, phone);

        if (existsUser){
            String existMesasge =
                    usersPersistence.findByPhone(phone) != null ?
                        MessageFormat.format("手机号{0}已存在", phone) :
                        usersPersistence.findByName(userName) != null ?
                            MessageFormat.format("用户名{0}已存在", userName) :
                                MessageFormat.format("邮箱{0}已存在", email);
            throw new ResourceExistException(ExceptionCode.USER_EXISTS.code(), existMesasge);
        }

        UserProfile up = new UserProfile();
        up.setEmail(email);
        up.setName(userName);
        up.setPhone(phone);
        usersPersistence.create(up);

        UserRegistration ur = new UserRegistration(up, passwd);
        registrationPersistence.create(ur);
        return new HashMap<String, Object>(){{
            put("userRegistration", ur);
            put("userProfile", up);
        }};
    }

    /**
     * TODO 需要拿用户的token
     * @param captcha
     * @return
     */
    private boolean isCaptchaValid(String captcha) {
        return !"invalid".equals(captcha);
    }
}
