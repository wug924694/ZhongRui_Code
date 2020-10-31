package com.zhongrui.filters;

import com.zhongrui.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZ_KEY = "Authorization";
    /**
     * 用于拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取用户的令牌信息
        //头文件中
        String token = request.getHeaders().getFirst(AUTHORIZ_KEY);
        //参数获取令牌
        if(StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZ_KEY);
        }
        //从cookie中取
        if(StringUtils.isEmpty(token)){
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZ_KEY);
            if(cookie != null){
                token = cookie.getValue();
            }
        }
        //如果没有令牌  则拦截
        if(StringUtils.isEmpty(token)){
            //设置状态信息   401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return response.setComplete();
        }
        //如果有令牌校验
        try {
           JwtUtil.parseJWT(token);
        } catch (Exception e) {//如果解析失败一定会抛异常
            //无效则拦截
            //设置状态信息   401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return response.setComplete();
        }
        //有效则放行
        return chain.filter(exchange);
    }

    /**
     * 排序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
