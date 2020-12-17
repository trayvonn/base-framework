package com.admin.common.starter.handler;

import cn.hutool.json.JSONUtil;
import com.admin.common.starter.annotation.RestWrapIgnore;
import com.admin.core.base.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 返回数据封装
 */
@RestControllerAdvice
public class RestResultHandler implements ResponseBodyAdvice<Object> {

    private String[] basePackages;

    public RestResultHandler(String[] basePackages){
        this.basePackages = basePackages;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        Method method = methodParameter.getMethod();
        if (Arrays.stream(basePackages).noneMatch(basePackage->declaringClass.getPackage().getName().startsWith(basePackage))) {
            //暂时不开启该开关
            return false;
        }

        //标注RestWrapIgnore注解的跳过
        RestWrapIgnore restWrapIgnore = declaringClass.getAnnotation(RestWrapIgnore.class);
        if (restWrapIgnore!=null||(method!=null && method.getAnnotation(RestWrapIgnore.class)!=null)) {
            return false;
        }

        // 类标注@RestController注解的所有接口返回值都需要特殊处理
        // 方法标注@ResponseBody注解的接口返回值需要特殊处理
        RestController restController = declaringClass.getAnnotation(RestController.class);

        return restController != null || (method != null && method.getAnnotation(ResponseBody.class) != null);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 接口返回的是R响应格式不用处理直接返回出去
        if (body instanceof R) {
            return body;
        }

        // 返回字符串要单独处理(HttpMessageConverter)
        if (body instanceof String || mediaType == MediaType.TEXT_PLAIN) {
            if (serverHttpResponse instanceof ServletServerHttpResponse) {
                serverHttpResponse.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            }
            return JSONUtil.toJsonStr(R.success(body));
        }

        return R.success(body);
    }
}
