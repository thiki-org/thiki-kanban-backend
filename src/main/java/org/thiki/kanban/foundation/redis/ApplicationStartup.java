package org.thiki.kanban.foundation.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xubt on 10/02/2017.
 */
@Component
@ConditionalOnProperty("redis.enabled")
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    public static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Flush all cache.");
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        logger.info("Flush all cache completed.");
    }
}
