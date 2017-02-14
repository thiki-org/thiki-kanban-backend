package org.thiki.kanban.foundation.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xubt on 14/02/2017.
 */
@Component
@ConditionalOnProperty("redis.enabled")
public class RedisScheduledTasks {
    public static Logger logger = LoggerFactory.getLogger(RedisScheduledTasks.class);
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "${redis.scheduled.flush.cron}")
    public void flushAll() {
        logger.info("ScheduledTask:Flushing all cache.");
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        logger.info("ScheduledTask:Flushing all cache completed.");
    }
}
