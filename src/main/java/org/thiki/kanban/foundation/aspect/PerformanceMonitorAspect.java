package org.thiki.kanban.foundation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bobo on 17/3/8.
 */
@Aspect
@Component
public class PerformanceMonitorAspect {
    Logger logger = LoggerFactory.getLogger(PerformanceMonitorAspect.class.getName());
    private static ConcurrentHashMap<String, MethodStats> methodStats = new ConcurrentHashMap<String, MethodStats>();
    private static long statLogFrequency = 10;
    private static long methodWarningThreshold = 1000;

    @Pointcut("execution(* org.thiki.*.*(..))")
    private void pointMethod() {
    }

    @Around("pointMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.print("11111111111");
        long start = System.currentTimeMillis();
        Object o = pjp.proceed();
        updateStats(pjp.getTarget().getClass()+"."+pjp.getSignature().getName(),(System.currentTimeMillis() - start));
        return o;
    }

    private void updateStats(String methodName, long elapsedTime) {
        MethodStats stats = methodStats.get(methodName);
        if(stats == null) {
            stats = new MethodStats(methodName);
            methodStats.put(methodName,stats);
        }
        stats.count++;
        stats.totalTime += elapsedTime;
        if(elapsedTime > stats.maxTime) {
            stats.maxTime = elapsedTime;
        }

        if(elapsedTime > methodWarningThreshold) {
            logger.warn("method warning: " + methodName + "(), cnt = " + stats.count + ", lastTime = " + elapsedTime + ", maxTime = " + stats.maxTime);
        }

        if(stats.count % statLogFrequency == 0) {
            long avgTime = stats.totalTime / stats.count;
            long runningAvg = (stats.totalTime-stats.lastTotalTime) / statLogFrequency;
            logger.debug("method: " + methodName + "(), cnt = " + stats.count + ", lastTime = " + elapsedTime + ", avgTime = " + avgTime + ", runningAvg = " + runningAvg + ", maxTime = " + stats.maxTime);
System.out.print("11111111111");
            //reset the last total time
            stats.lastTotalTime = stats.totalTime;
        }
    }

    class MethodStats {
        public String methodName;
        public long count;
        public long totalTime;
        public long lastTotalTime;
        public long maxTime;

        public MethodStats(String methodName) {
            this.methodName = methodName;
        }
    }
}
