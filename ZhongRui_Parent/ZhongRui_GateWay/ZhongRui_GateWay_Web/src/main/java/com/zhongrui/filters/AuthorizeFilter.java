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

/**
 * 1.判断是否有令牌
 * 2.将令牌存储到指定的头文件中
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZ_TOKEN = "Authorization";
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
        String token = request.getHeaders().getFirst(AUTHORIZ_TOKEN);

        //boolean true 令牌在头文件中  false  令牌不在头文件中  --》将令牌封装到头文件中  在传递到其他的微服务
        boolean hasToken = true;

        //参数获取令牌
        if(StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZ_TOKEN);
            hasToken = false;
        }
        //从cookie中取
        if(StringUtils.isEmpty(token)){
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZ_TOKEN);
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
        }else{
            if(!hasToken){
                //判读当前令牌是否有这个bearer前缀  如果没有 则我们添加前缀
                if(!token.startsWith("bearer") && !token.startsWith("Bearer")){//没有前缀
                    token = "bearer " + token;
                }
                //将头文件封装到指定的头中
                //将令牌封装到头文件中
                request.mutate().header(AUTHORIZ_TOKEN,token);
            }
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
