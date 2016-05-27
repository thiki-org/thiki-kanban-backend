package org.thiki.kanban.entrance;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
@RequestMapping(value = "/entrance")
public class EntranceController {
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity enter() throws Exception {
        return Response.build(new EntranceResource());
    }
}
