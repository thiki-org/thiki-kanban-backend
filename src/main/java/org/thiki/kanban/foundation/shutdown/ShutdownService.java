package org.thiki.kanban.foundation.shutdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.HTTPUtil;
import org.thiki.kanban.foundation.configuration.ApplicationContextProvider;

/**
 * Created by xubt on 11/21/16.
 */
@Service
public class ShutdownService {
    public static Logger logger = LoggerFactory.getLogger(ShutdownService.class);

    public void shutdown(String remoteAddr) {
        logger.info("The remoteAddr is:" + remoteAddr);
        logger.info("The service will be closed 15 seconds later.");
        if (!HTTPUtil.isLocalRequest(remoteAddr)) {
            return;
        }
        SpringApplication.exit(ApplicationContextProvider.getApplicationContext());
        logger.info("The service was closed successfully.");
    }
}
