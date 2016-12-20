package org.thiki.kanban.foundation.logback;

/**
 * Created by xubt on 20/12/2016.
 */

import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thiki.kanban.foundation.common.SequenceNumber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    private final static String SESSION_KEY = "sessionId";

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        MDC.remove(SESSION_KEY);
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SequenceNumber sequenceNumber = new SequenceNumber();
        String token = sequenceNumber.generate();
        MDC.put(SESSION_KEY, token);
        return true;
    }
}
