package com.zhongrui.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/*****
 * @Author: www.youyue
 * @Date: 2019/7/7 13:48
 * @Description: com.zhongrui.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjpudWxsLCJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiY29tcGFueSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MjAzNjc5OTEzNCwiYXV0aG9yaXRpZXMiOlsidmlwIiwidXNlciJdLCJqdGkiOiIzZjFiM2ZjNC1iMDhiLTQyNjUtYjlkNi04ZTk0Y2UwNzZkZTEiLCJjbGllbnRfaWQiOiJ6aG9uZ3J1aSIsInVzZXJuYW1lIjoidGFuZ3NlbmcifQ.RoH_443ojCvbC8idUdSlwZqTc7WUMd_o1BD4sRRqTkreUcv8fa7shqo1BiU8xjTUrhBQqnuP02-yP5EZAlTAUajLFR8ODjXuRjzBpByhj8pitnGuENewcRihqIdN8DcTlovrgzUSaxQ_Qkx-mqdT2iZvlcvGtg2PSRH1MQ_r96pABE08ReYipQ6I6fr2oR_tqHdCttY6pzVZw1Vucoz8qtTwwSfNDqqcMvLWjZqirn8klfInr6R3SrV8WdESGoJ_ZE6dLf5I7hXKHQ5USbK2K3ms8ZFLbMfeJUuxtRQa8CibqYoRN1yB0sDQtm8J95ygnOoufFbmon3vye5B7r2Ytg";
         //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgEdQEIUZSsH9TZTYQrU8r+nChFwNu6KVf2z4C3CmDnMwY3MVpWyST99OzcTJBZt/4XvHVUeAEQ+a5yNLmLqkHvtBKe4E0aisl5s8wH+p5Y21KQoWiegw5XEaRfjmLVL7tH9G9mdy5VndSnSOGZHEtad3CIT8f5MUusi2G7Q/WM/1ga7AWuDHkGBsy4GRMwpKsm+/H2W5WAC0zE0EyG0nVFgbu6xAnC32NvMY4pAcFrqRTFBrO+ageuIhjsm1vlrmW35wNhO8FwCG5ExvD8iQXh6Kf1V6/Z92f8hYyayY+llsX7LEI1ZYYOrz6gltfWkymYOughlD7gf5q852Z4cJ6QIDAQAB-----END PUBLIC KEY-----";
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }

    @Test
    public void testBasicBase64() throws UnsupportedEncodingException {
        //这段内容其实就是客户端ID和秘钥 加密之后的结果
        String key = "emhvbmdydWk6emhvbmdydWk=";
        byte[] bytes = Base64.getDecoder().decode(key.getBytes());
        String deco = new String(bytes,"utf-8");
        System.out.println(deco);

        //Basic Base64(客户端ID:秘钥)  zhongrui:zhongrui
        //将来用户在进行用户名个密码登录时需要生成令牌的时候 我们可以自动帮助他加入这个客户端ID和秘钥

        //请求头  Authorizationed=Basic emhvbmdydWk6emhvbmdydWk=
    }
}
