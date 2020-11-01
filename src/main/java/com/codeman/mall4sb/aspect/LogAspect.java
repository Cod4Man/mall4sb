package com.codeman.mall4sb.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author: zhanghongjie
 * @description: springAop拦截生成日志
 * @date: 2020/7/15 21:32
 * @version: 1.0
 */
@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 切面
    // * com.codeman.blog.web.*.*(..))
    // 所有访问权限(public/private/..) 包.类.方法(所有参数)
    @Pointcut("execution(* com.codeman.mall4sb.controller.*.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer url = request.getRequestURL();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] parms = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(ip, url.toString(), classMethod, parms);
        logger.debug("RequestLog: {}", requestLog);
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.debug("Result: {}", result);
    }

    private class RequestLog {
        private String ip;
        private String url;
        private String classMethod;
        private Object[] parms;

        public RequestLog(String ip, String url, String classMethod, Object[] parms) {
            this.ip = ip;
            this.url = url;
            this.classMethod = classMethod;
            this.parms = parms;
        }

        @Override
        public String toString() {
            return "{" +
                    "ip='" + ip + '\'' +
                    ", url='" + url + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", parms=" + Arrays.toString(parms) +
                    '}';
        }
    }

}
