package com.zhongrui.entity;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * 订单微服务  从上一个服务中接收令牌  存储到指定的头 在发送
 */
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //记录了当前用户的所有记录  包含请求头和请求参数等
        //tomcat  线程池  线程  同时feign开启熔断保护
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //信号量
        /**
         * 获取头中的数据
         * 获取所有头的名称
         */
        Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
        while (headerNames.hasMoreElements()){
            //获取请求头的key
            String headerKey = headerNames.nextElement();
            //获取请求头的值
            String headerValue = requestAttributes.getRequest().getHeader(headerKey);
            System.out.println(headerKey+"==="+headerValue);
            //存储到指定的头中
            requestTemplate.header(headerKey,headerValue);
        }
    }
}
