package com.zhongrui.auth.interceptor;

import com.zhongrui.auth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * 拦截器有什么特点？   怎样才算是一个拦截器？  什么时候执行？  请求来临执行之前？  请求执行之后？  还是在feign调用之前？
 */
@Configuration
public class TokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        /**
         *   分析  面临的问题是现在的认证服务可以通过feign发起远程调用user服务  那是应为user服务做了放行  不推荐
         *   期望在feign发起远程调用的时候  传递令牌    拦截器
         *   1.没有令牌  feign在调用之前   生成令牌
         *   2.feign在调用之前  令牌需要携带过去
         *   3.feign在调用之后  令牌还需要存储到指定的头当中
         *   4.请求--》feign调用--》拦截器RequestInterceptor-->feign调用之前放行
         */
        //生成admin令牌
        String token = AdminToken.adminToken();
        requestTemplate.header("Authorization","bearer "+token);

    }
}
