package org.thiki.kanban.foundation.logback;

/**
 * Created by xubt on 20/12/2016.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thiki.kanban.foundation.common.SequenceNumber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor extends HandlerInterceptorAdapter {
    private final static String SESSION_KEY = "sessionId";
    private final static String IP_KEY = "ip";
    private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        logger.info("remove sessionId.");
        MDC.remove(SESSION_KEY);
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SequenceNumber sequenceNumber = new SequenceNumber();
        String sessionId = sequenceNumber.generate();
        String ip = request.getRemoteHost();
        MDC.put(SESSION_KEY, sessionId);
        MDC.put(IP_KEY, ip);
        logger.info("init session:{}", sessionId);
        return true;
    }
}
