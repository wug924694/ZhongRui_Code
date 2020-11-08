package com.zhongrui.auth.config;
import com.zhongrui.auth.util.UserJwt;
import com.zhongrui.entity.Result;
import com.zhongrui.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/*****
 * 自定义授权认证类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Resource
    private UserFeign userFeign;

    /****
     * 自定义授权认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                //静态方式
                /*return new User(username,new BCryptPasswordEncoder().
                        encode(clientSecret),
                        AuthorityUtils.
                                commaSeparatedStringToAuthorityList(""));*/
                //数据库查找方式  authentication中获取秘钥
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }

        if (StringUtils.isEmpty(username)) {
            return null;
        }

        /**
         *   分析  面临的问题是现在的认证服务可以通过feign发起远程调用user服务  那是应为user服务做了放行  不推荐
         *   期望在feign发起远程调用的时候  传递令牌    拦截器
         *   1.没有令牌  feign在调用之前   生成令牌
         *   2.feign在调用之前  令牌需要携带过去
         *   3.feign在调用之后  令牌还需要存储到指定的头当中
         *   4.请求--》feign调用--》拦截器RequestInterceptor-->feign调用之前放行
         */
        Result<com.zhongrui.user.pojo.User> userResult = userFeign.findById(username);
        //有可能这个用户只有用户名  没有密码
        if(userResult == null  || userResult.getData().getPassword() == null){
            return null;
        }

        //根据用户名查询用户信息
        String pwd = userResult.getData().getPassword();
        //创建User对象  自行完成
        String permissions = "user,vip";


        UserJwt userDetails = new UserJwt(username,pwd,AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));


        //userDetails.setComy(songsi);
        return userDetails;
    }
}
