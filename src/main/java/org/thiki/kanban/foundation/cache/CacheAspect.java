package org.thiki.kanban.foundation.cache;

import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriTemplate;

/**
 * Created by xubt on 10/24/16.
 */
@Aspect
@Component
public class CacheAspect {
    private static Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @CacheEvict(value = "board", key = "contains('board-overall'+#result)", condition = "#result !=null ")
    @After("within(@org.springframework.web.bind.annotation.RestController *) && execution(* *(..))")
    public String advice() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        RequestFacade requestFacade = (RequestFacade) servletRequestAttributes.getRequest();
        String method = requestFacade.getMethod();
        if (HttpMethod.GET.name().equals(method)) {
            return null;
        }
        String userName = requestFacade.getHeader("userName");
        if (userName == null) {
            return null;
        }
        String uri = requestFacade.getRequestURI();
        UriTemplate template = new UriTemplate("boards/{boardId}");
        String extractedUrl = template.match(uri).get("boardId");
        if (extractedUrl == null) {
            return null;
        }
        String boardId = template.match(uri).get("boardId").split("/")[0];
        logger.info("board overall cache was removed.boardId:{}", boardId);
        return boardId;
    }
}
