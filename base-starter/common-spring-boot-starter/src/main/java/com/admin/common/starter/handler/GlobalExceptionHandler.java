package com.admin.common.starter.handler;

import cn.hutool.json.JSONUtil;
import com.admin.common.starter.aspect.LogAspect;
import com.admin.common.starter.exception.BusinessException;
import com.admin.core.base.R;
import com.admin.core.enums.HttpStatus;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 入参格式错误异常.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getCause() != null) {
            if (e.getCause() instanceof JsonParseException) {
                return R.fail(HttpStatus.BAD_REQUEST, "JSON格式错误");
            }
        }

        return R.fail(HttpStatus.INTERNAL_EXCEPTION);
    }

    /**
     * 入参校验异常.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R<?> handleConstraintViolationException(ConstraintViolationException e) {
        List<ConstraintViolation<?>> violationList = new ArrayList<>(e.getConstraintViolations());
        ConstraintViolation<?> violation = violationList.get(0);
        return R.fail(HttpStatus.BAD_REQUEST, violation.getMessage());
    }

    /**
     * 入参对象校验异常.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError != null) {
            return R.fail(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
        }
        return R.fail(HttpStatus.BAD_REQUEST);
    }

    /**
     * 入参对象校验异常.
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public R<?> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError != null) {
            return R.fail(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
        }
        return R.fail(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public R<?> handleMissingServletRequestParameterException() {
        return R.fail(HttpStatus.BAD_REQUEST);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public R<?> handleBusinessException(BusinessException ex) {
        return R.fail(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R<?> handleUnknownException(HttpServletRequest request,Exception ex) {
        log.error("内部异常!", ex);
        recordDetailIfWeb(request);
        return R.exception();
    }

    /**
     * 记录web环境下的内部异常详细信息.
     */
    private void recordDetailIfWeb(HttpServletRequest request) {
        if (request == null) {
            return;
        }

        Object requestId = request.getAttribute(LogAspect.REQUEST_ID_NAME);

        String requestURI = request.getRequestURI();
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new LinkedHashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.error("({})-请求URI: {}, 请求头: {}, 请求参数: {}", requestId, requestURI, JSONUtil.toJsonStr(headerMap), JSONUtil.toJsonStr(parameterMap));
    }
}
