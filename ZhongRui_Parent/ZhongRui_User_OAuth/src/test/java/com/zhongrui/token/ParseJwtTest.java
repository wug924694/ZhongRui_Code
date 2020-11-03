package com.zhongrui.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

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
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJ5b3V5dWUiLCJpZCI6IjEifQ.R6ISBEtUitmt0y6mHXCmu7EbdiyM-ts2YjGgEaOr_ce1gFd7F_pNY57jyhROf5qoxSpx_9dXjvVyesqF7BOsgMdemmFyKya9vGco-gZe0fQAyjICDawlHuOUa1bUaMx92WAVkoGDjqyQS9B98n3TdP6Dub35bv4LNOLvGBwNg2VfBWA0ml95dt00874YpW3ILZSQoVML-9e6SGfe7uLKDwxeeJyW0dj7XDCTtIRM0D-fSXKrg24f2iYhk5Cgyv0k9sNgtk4qMPtA6Cr0T7NQ1w_Vi7mu2pehiOxpwY7R5ZLfVGh0qmux9PvDBUenCu2DihZPNdBD_3dQME7-2OOnHQ";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp6rJLg2ExkNY4jiZHN1N5r8XzDWP0pQk5fnlc7xNiB64aX5ri4eqmpb/3VfxB0rq44NH6BI8jQbUg0QzrALNstXLDR0gpBgrwmhaxZ7JK9b/DEoraAsw9bB/jrFLXnptGLw2xeeHQ6NkV2A+ByDOR8sMSLhGIzthiN3Cj6XND/nb0Me8jWBiPVmoQ8uHcy16WPB4kfaKEoysd6yA91i6rq+igTxhhZEzhR1mt5fEiHglTrhjkknVqH2oNfi0KouBI/zCRfAhCARTfFWdf/foi8VOxgzcYlWJ1Y0xc4eTB3Y8M2rYla0f9Qt7pel3WbKYUkdWvi8eOaWAsfAjHuNBRwIDAQAB-----END PUBLIC KEY-----";
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
