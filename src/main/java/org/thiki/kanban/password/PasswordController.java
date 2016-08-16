package org.thiki.kanban.password;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;
import org.thiki.kanban.password.password.PasswordResource;
import org.thiki.kanban.password.password.PasswordService;
import org.thiki.kanban.password.passwordReset.PasswordReset;
import org.thiki.kanban.password.passwordReset.PasswordResetApplication;
import org.thiki.kanban.password.passwordReset.PasswordResetResource;
import org.thiki.kanban.password.passwordRetrieval.PasswordRetrievalApplication;
import org.thiki.kanban.password.passwordRetrieval.PasswordRetrievalResource;

import javax.annotation.Resource;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
public class PasswordController {
    @Resource
    private PasswordService passwordService;

    @RequestMapping(value = "/passwordRetrievalApplication", method = RequestMethod.POST)
    public HttpEntity passwordRetrievalApply(@RequestBody PasswordRetrievalApplication passwordRetrievalApplication) throws Exception {
        passwordService.createPasswordRetrievalApplication(passwordRetrievalApplication);
        return Response.post(new PasswordRetrievalResource());
    }

    @RequestMapping(value = "/passwordResetApplication", method = RequestMethod.POST)
    public HttpEntity passwordRetrieval(@RequestBody PasswordResetApplication passwordResetApplication) throws Exception {
        passwordService.createPasswordResetApplication(passwordResetApplication);
        return Response.post(new PasswordResetResource());
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public HttpEntity password(@RequestBody PasswordReset passwordReset) throws Exception {
        passwordService.resetPassword(passwordReset);
        return Response.post(new PasswordResource());
    }
}
