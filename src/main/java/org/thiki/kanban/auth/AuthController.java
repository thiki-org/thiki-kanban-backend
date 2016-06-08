package org.thiki.kanban.auth;

import cn.xubitao.dolphin.foundation.response.Response;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.entrance.EntranceResource;
import org.thiki.kanban.entrance.EntranceResourceAssembler;

//只列了框架 并未实现
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    //用户登陆后，根据用户校验权限，并返回token ，token 加密方式为client 的单独key。token 内容：userid|is_login|token expires|data（相关信息）|4位随机值
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> Login() throws Exception {
        return Response.build(new EntranceResource(), new EntranceResourceAssembler());
    }

    //刷新token，token 加密方式为client 的单独key。token 内容：userid|is_login|token expires|data（相关信息）|4位随机值
    @RequestMapping(value = "/refresh_token",method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> refresh_token() throws Exception {
        return Response.build(new EntranceResource(), new EntranceResourceAssembler());
    }
}
