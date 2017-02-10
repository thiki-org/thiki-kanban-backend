package org.thiki.kanban.foundation.redis;

import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriTemplate;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by xubt on 10/24/16.
 */
@Aspect
@Component
@ConditionalOnProperty("redis.enabled")
public class CacheAspect {
    private static Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @After("within(@org.springframework.web.bind.annotation.RestController *) && execution(* *(..))")
    public void advice() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        RequestFacade requestFacade = (RequestFacade) servletRequestAttributes.getRequest();
        String method = requestFacade.getMethod();
        if (HttpMethod.GET.name().equals(method)) {
            return;
        }
        String userName = requestFacade.getHeader("userName");
        if (userName == null) {
            return;
        }
        String uri = requestFacade.getRequestURI();
        UriTemplate template = new UriTemplate("boards/{boardId}");
        String extractedUrl = template.match(uri).get("boardId");
        if (extractedUrl == null) {
            return;
        }
        String boardId = template.match(uri).get("boardId").split("/")[0];
        logger.info("board snapshot cache was removed.boardId:{}", boardId);
        String keyPattern = String.format("board-snapshot%s*", boardId);
        Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys(keyPattern.getBytes());
        for (byte[] key : keys) {
            redisTemplate.delete(new String(key, 0, key.length));
        }
    }
}
