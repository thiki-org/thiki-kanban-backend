package org.thiki.kanban.foundation.shutdown;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by xubt on 11/21/16.
 */
@RestController
public class ShutdownController {
    @Resource
    public ShutdownService shutdownService;

    @RequestMapping(value = "/shutdown", method = RequestMethod.POST)
    public void shutdown(HttpServletRequest request) throws Exception {
        shutdownService.shutdown(request.getRemoteAddr());
    }
}
