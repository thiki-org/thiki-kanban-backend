package org.thiki.kanban.foundation.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by xubt on 8/23/16.
 */
@Controller
@RequestMapping(value = "/error")
@EnableConfigurationProperties({ServerProperties.class})
public class ExceptionController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;


    /**
     * 初始化ExceptionController
     *
     * @param errorAttributes
     */
    @Autowired
    public ExceptionController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = "404")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error404(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request));
        HttpStatus status = getStatus(request, isIncludeStackTrace(request));
        return new ResponseEntity<>(body, status);
    }

    @RequestMapping(value = "businessException")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> businessException(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request));
        HttpStatus status = getStatus(request, isIncludeStackTrace(request));
        return new ResponseEntity<>(body, status);
    }

    @RequestMapping(value = "401")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error401(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request));
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "invalidParamsException")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> illegalArgumentException(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "500")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error500(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request));
        HttpStatus status = getStatus(request, isIncludeStackTrace(request));
        return new ResponseEntity<>(body, status);
    }

    private boolean isIncludeStackTrace(HttpServletRequest request) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        return include == ErrorProperties.IncludeStacktrace.ALWAYS || include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM && getTraceParameter(request);
    }


    /**
     * 获取错误的信息
     *
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
        if (this.errorAttributes.getError(requestAttributes) instanceof BusinessException) {
            errorAttributes.put("code", ((BusinessException) this.errorAttributes.getError(requestAttributes)).getCode());
        }
        if (this.errorAttributes.getError(requestAttributes) instanceof AuthenticationException) {
            errorAttributes.put("code", ((AuthenticationException) this.errorAttributes.getError(requestAttributes)).getCode());
        }
        return errorAttributes;
    }

    /**
     * 是否包含trace
     *
     * @param request
     * @return
     */
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        return parameter != null && !"false".equals(parameter.toLowerCase());
    }

    /**
     * 获取错误编码
     *
     * @param request
     * @param includeStackTrace
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        if (this.errorAttributes.getError(requestAttributes) instanceof BusinessException) {
            return ((BusinessException) this.errorAttributes.getError(requestAttributes)).getStatus();
        }
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
