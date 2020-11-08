package com.zhongrui.auth.service;


import com.zhongrui.auth.util.AuthToken;

/*****
 * @Author: www.youyue
 * @Date: 2019/7/7 16:23
 * @Description: com.zhongrui.oauth.service
 ****/
public interface AuthService {

    /***
     * 授权认证方法
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);
}
