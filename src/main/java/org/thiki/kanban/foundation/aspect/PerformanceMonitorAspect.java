package org.thiki.kanban.foundation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bobo on 17/3/8.
 */
@Aspect
@Component
@ConditionalOnProperty("performanceMonitor.enabled")
public class PerformanceMonitorAspect {
    private static ConcurrentHashMap<String, MethodStatistics> methodStats = new ConcurrentHashMap<String, MethodStatistics>();
    private static long statLogFrequency = 10;
    private static long methodWarningThreshold = 1000;
    Logger logger = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    @Pointcut("execution(*  org.thiki.kanban..*.*(..))")
    private void pointMethod() {
    }

    @Around("pointMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        }
        finally{
                updateStats(pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName(), (System.currentTimeMillis() - start));
            }
    }

    private void updateStats(String methodName, long elapsedTime) {
        MethodStatistics methodStatistics = PerformanceMonitorAspect.methodStats.get(methodName);
        if (methodStatistics == null) {
            methodStatistics = new MethodStatistics(methodName);
            PerformanceMonitorAspect.methodStats.put(methodName, methodStatistics);
        }
        methodStatistics.executeTimes++;
        methodStatistics.totalTime += elapsedTime;
        if (elapsedTime > methodStatistics.maxTime) {
            methodStatistics.maxTime = elapsedTime;
        }

        if(elapsedTime > methodWarningThreshold) {
            logger.warn("method warning: " + methodName + "(), executeTimes = " + methodStatistics.executeTimes + ", lastTime = " + elapsedTime + ", maxTime = " + methodStatistics.maxTime);
        }

        if (methodStatistics.executeTimes % statLogFrequency == 0) {
            long averageTime = methodStatistics.totalTime / methodStatistics.executeTimes;
            long runningAvg = (methodStatistics.totalTime - methodStatistics.lastTotalTime) / statLogFrequency;
            logger.debug("method: " + methodName + "(), executeTimes = " + methodStatistics.executeTimes + ", lastTime = " + elapsedTime + ", avgTime = " + averageTime + ", runningAvg = " + runningAvg + ", maxTime = " + methodStatistics.maxTime);
            //reset the last total time
            methodStatistics.lastTotalTime = methodStatistics.totalTime;
        }
    }

    class MethodStatistics {
        public String methodName;
        public long executeTimes;
        public long totalTime;
        public long lastTotalTime;
        public long maxTime;

        public MethodStatistics(String methodName) {
            this.methodName = methodName;
        }
    }
}
