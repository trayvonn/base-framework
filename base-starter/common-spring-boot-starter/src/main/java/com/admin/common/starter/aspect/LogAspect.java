package com.admin.common.starter.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.admin.common.starter.annotation.RequestLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 记录接口日志切面
 *
 * @author 吴邪
 * @date 2020/6/5 14:26
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    public static final String REQUEST_ID_NAME = "_Request_id";

    @SuppressWarnings("unchecked")
    @Around("@annotation(requestLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RequestLog requestLog) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Map<String, Object> pointCutInfo = getPointCutInfo(proceedingJoinPoint);

        String className = Convert.toStr(pointCutInfo.get("className"));
        String methodName = Convert.toStr(pointCutInfo.get("methodName"));

        String requestId = null;// getRequestId();

        Map<Class<?>, Object> args = (Map<Class<?>, Object>) pointCutInfo.get("args");
        log.info("执行开始({})->{}.{}({})", requestId, className, methodName, getArgsToString(args));
        StopWatch stopWatch = new StopWatch(StrUtil.concat(true, className, "#", methodName));
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        log.info(stopWatch.shortSummary());

        return result;
    }


    /**
     * 获取连接点信息.
     *
     * @param joinPoint 连接点
     * @return 连接点信息
     */
    private static Map<String, Object> getPointCutInfo(JoinPoint joinPoint) {

        // 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        // 方法形参类型
        StringBuilder methodArgTypes = new StringBuilder();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class[] parameterTypes = signature.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            methodArgTypes.append(parameterTypes[i].getSimpleName());
            if (i != parameterTypes.length - 1) {
                methodArgTypes.append(", ");
            }
        }
        // 方法实参
        Object[] args = joinPoint.getArgs();
        Map<Class<?>, Object> argMap = new LinkedHashMap<>(args.length);

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class<?> argType = parameterTypes[i];
            argMap.put(argType, arg);
        }


        Map<String, Object> map = new HashMap<>(4);
        map.put("className", className);
        map.put("methodName", methodName);
        map.put("methodArgTypes", methodArgTypes);
        map.put("args", argMap);

        return map;
    }

    private String getArgsToString(Map<Class<?>, Object> argMap) {
        StringBuilder sb = new StringBuilder();
        List<Map.Entry<Class<?>, Object>> args = new ArrayList<>(argMap.entrySet());

        int size = args.size();
        for (int i = 0; i < size; i++) {
            Map.Entry<Class<?>, Object> arg = args.get(i);
            String typeName = arg.getKey().getSimpleName();
            Object value = arg.getValue();
            sb.append(typeName);
            sb.append(":");
            if (value != null) {
                try {
                    sb.append(JSONUtil.toJsonStr(arg.getValue()));
                } catch (Exception ignored) {
                }
            } else {
                sb.append("null");
            }
            if (i != size - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    private static String getRequestId() {
        HttpServletRequest httpRequest;
        if ((httpRequest = getHttpRequest()) != null) {
            Object requestId = httpRequest.getAttribute(REQUEST_ID_NAME);
            if (requestId == null) {
                requestId = RandomUtil.randomString(8);
                httpRequest.setAttribute(REQUEST_ID_NAME, requestId);
            }
            return (String) requestId;
        }
        return null;
    }

    private static HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (requestAttributes != null) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        return null;
    }
}
