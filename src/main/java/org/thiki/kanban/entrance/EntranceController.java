package org.thiki.kanban.entrance;

import cn.xubitao.dolphin.foundation.response.Response;

import org.thiki.kanban.controller.EntranceResource;
import org.thiki.kanban.entrance.EntranceResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
