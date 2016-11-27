package org.thiki.kanban.entrance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(EntranceController.class);

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity enter() throws Exception {
        logger.warn("entrance called!");
        return Response.build(new EntranceResource());
    }
}
