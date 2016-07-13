package org.thiki.kanban.login;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.config.ValidateParams;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.foundation.security.token.TokenService;
import org.thiki.kanban.registration.Registration;
import org.thiki.kanban.registration.RegistrationPersistence;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class LoginService {
    @Resource
    private RegistrationPersistence registrationPersistence;
    @Resource
    private TokenService tokenService;

    @ValidateParams
    public Identification login(@NotEmpty(message = "Identity is required.") String identity, String password) throws Exception {
        Registration registeredUser = registrationPersistence.findByName(identity);
        String rsaDecryptedPassword = RSAService.decrypt(password);
        String md5Password = MD5Service.encrypt(rsaDecryptedPassword + registeredUser.getSalt());

        Registration matchedUser = registrationPersistence.findByIdentity(identity, md5Password);
        if (matchedUser == null) {
            throw new InvalidParameterException("Your username or password is incorrect.");
        }

        Identification identification = new Identification();
        String token = tokenService.buildToken(matchedUser.getName());
        identification.setToken(token);

        return identification;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ExecutableValidator executableValidator = factory.getValidator().forExecutables();
        Object object = LoginService.class;
        Method method = LoginService.class.getMethod("login", String.class, String.class);
        Object[] parameterValues = {null, null};
        Set<ConstraintViolation<Object>> violations = executableValidator.validateParameters(
                new LoginService(),
                method,
                parameterValues
        );

        assertEquals(1, violations.size());
    }

}
