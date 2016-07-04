package org.thiki.kanban.registration;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by joeaniu on 6/21/16.
 */
@RestController
public class RegistrationController {
    @Resource
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public HttpEntity registerNewUser(@RequestBody Map<String, String> registrationForm, @RequestHeader String token) {
        Map<String, Object> result = registrationService.registerNewUser(
                registrationForm.get("name"),
                registrationForm.get("email"),
                registrationForm.get("phone"),
                registrationForm.get("password"),
                registrationForm.get("captcha")
        );
        RegistrationResource res = new RegistrationResource(result);
        res.add(linkTo(methodOn(RegistrationController.class).registerNewUser(registrationForm, token)).withSelfRel());

        return Response.post(res);

    }

    /*
     * 验证用户
     */
    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, String> userForm){
        //获取到当前用户的的Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userForm.get("name"), userForm.get("password"));
        String error = null;
        try {
            //验证登陆
            subject.login(token);
        } catch (AuthenticationException e) {
            error = "warning message:Login failed,please check your username and password";
        }
        if (error==null) {
            return "redirect:../index/index";
        } else {
            return "redirect:index";
        }
    }

}
