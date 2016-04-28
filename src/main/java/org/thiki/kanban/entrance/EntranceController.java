package org.thiki.kanban.entrance;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.xubitao.dolphin.foundation.response.Response;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
@RequestMapping(value = "/entrance")
public class EntranceController {
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> enter() throws Exception {
        return Response.build(new EntranceResource(), new EntranceResourceAssembler());
    }
}
