package org.thiki.kanban.foundation.exception;

import cn.xubitao.dolphin.foundation.exceptions.ClientException;
import cn.xubitao.dolphin.foundation.response.Response;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xubitao on 2016/1/22.
 */
public class KanbanExceptionResolver extends SimpleMappingExceptionResolver {
    public KanbanExceptionResolver() {
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception exception) {
        try {
            int status = isClientError(exception) ? 400 : 500;

            response.reset();
            response.setStatus(status);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(Response.error(exception).toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isClientError(Exception exception) {
        if (exception instanceof HttpRequestMethodNotSupportedException) return true;
        return exception instanceof ClientException;
    }
}
