package org.thiki.kanban.foundation.redirection;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;

/**
 * Created by xubitao on 05/26/16.
 */
@RestController
public class UnauthorisedController {

    @RequestMapping(value = "/unauthorised", method = RequestMethod.GET)
    public HttpEntity unauthorised(@RequestParam("code") String code, @RequestParam("message") String message) throws Exception {
        Error error = new Error(code, message);

        return Response.unauthorised(new UnauthorisedResource(error));
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public HttpEntity error(@RequestParam("code") String code, @RequestParam("message") String message) throws Exception {
        Error error = new Error(code, message);

        return Response.error(new UnauthorisedResource(error));
    }
}
