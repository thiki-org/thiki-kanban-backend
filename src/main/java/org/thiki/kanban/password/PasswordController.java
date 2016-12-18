package org.thiki.kanban.password;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
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
    @Resource
    private PasswordResource passwordResource;
    @Resource
    private PasswordRetrievalResource passwordRetrievalResource;

    @Resource
    private PasswordResetResource passwordResetResource;

    @RequestMapping(value = "/passwordRetrievalApplication", method = RequestMethod.POST)
    public HttpEntity passwordRetrievalApply(@RequestBody PasswordRetrievalApplication passwordRetrievalApplication) throws Exception {
        String userName = passwordService.applyRetrieval(passwordRetrievalApplication);
        return Response.post(passwordRetrievalResource.toResource(userName));
    }

    @RequestMapping(value = "/{userName}/passwordResetApplication", method = RequestMethod.POST)
    public HttpEntity passwordRetrieval(@RequestBody PasswordResetApplication passwordResetApplication, @PathVariable("userName") String userName) throws Exception {
        passwordService.applyReset(userName, passwordResetApplication);
        return Response.post(passwordResetResource.toResource(userName));
    }

    @RequestMapping(value = "/{userName}/password", method = RequestMethod.PUT)
    public HttpEntity password(@RequestBody PasswordReset passwordReset, @PathVariable("userName") String userName) throws Exception {
        passwordService.resetPassword(userName, passwordReset);
        return Response.post(passwordResource.toResource());
    }
}
