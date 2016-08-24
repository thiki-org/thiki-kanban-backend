package org.thiki.kanban.foundation.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    /**
     * 定义404的ModelAndView
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(produces = "text/html", value = "404")
    public ModelAndView errorHtml404(HttpServletRequest request,
                                     HttpServletResponse response) {
        response.setStatus(getStatus(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)).value());
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/404", model);
    }

    /**
     * 定义404的JSON数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "404")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error404(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    /**
     * 定义500的ModelAndView
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(produces = "text/html", value = "500")
    public ModelAndView errorHtml500(HttpServletRequest request,
                                     HttpServletResponse response) {
        response.setStatus(getStatus(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)).value());
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/500", model);
    }

    @RequestMapping(value = "businessException")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error401(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    @RequestMapping(value = "invalidParamsException")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> illegalArgumentException(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ResponseEntity<Map<String, Object>>(body, HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "500")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error500(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ResponseEntity<Map<String, Object>>(body, status);
    }


    /**
     * Determine if the stacktrace attribute should be included.
     *
     * @param request  the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request,
                                          MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }


    /**
     * 获取错误的信息
     *
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
        if (this.errorAttributes.getError(requestAttributes) instanceof BusinessException) {
            errorAttributes.put("code", ((BusinessException) this.errorAttributes.getError(requestAttributes)).getCode());
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
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
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
