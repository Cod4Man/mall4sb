package com.codeman.mall4sb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhanghongjie
 * @description: 全局异常处理类
 * @date: 2020/7/5 21:12
 * @version: 1.0
 */
@ControllerAdvice // 会拦截所有的@Controller类
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class) // 标注这是处理异常信息
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request url:{}, exception: {}", request.getRequestURL(), e);

        // 释放部分异常给SpringBoot处理
        // 捕捉异常是否有ResponseStatus注解，那其实可以自定义注解
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            // 抛出异常，让Spring自己处理
            throw e;
        }
        ModelAndView modelAndView = new ModelAndView(); // Model and View
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error"); // 异常定向到得页面
        return modelAndView;
    }
}
