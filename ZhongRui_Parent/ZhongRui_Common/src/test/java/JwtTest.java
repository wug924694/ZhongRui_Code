import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    /**
     * 创建令牌
     */
    @Test
    public void testCreateToken(){
        //构建jwt令牌的对象
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuer("成都理想中心");//颁发者
        jwtBuilder.setIssuedAt(new Date()); //颁发时间
        jwtBuilder.setSubject("jwt令牌测试");//主题信息
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+80000));//过期时间

        //自定义载荷
        Map<String,Object> map = new HashMap<>();
        map.put("name","laowang");
        map.put("address","桥洞");
        map.put("money",3500);
        jwtBuilder.addClaims(map);

        jwtBuilder.signWith(SignatureAlgorithm.HS256,"youyue");//1.签名算法 2.秘钥
        String compact = jwtBuilder.compact();
        System.out.println(compact);//每次生成的令牌是否一样  是  否
        /**
         * eyJhbGciOiJIUzI1NiJ9.
         * eyJpc3MiOiLmiJDpg73nkIbmg7PkuK3lv4MiLCJpYXQiOjE2MDQxMjg0ODgsInN1YiI6Imp3dOS7pOeJjOa1i-ivlSJ9.
         * CwdrPhYq_VVXPP-ATzD1oVwhkjnXyWsaWb5DWdCOx0Y
         */
    }

    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLmiJDpg73nkIbmg7PkuK3lv4MiLCJpYXQiOjE2MDQxMjkyOTcsInN1YiI6Imp3dOS7pOeJjOa1i-ivlSIsImV4cCI6MTYwNDEyOTM3NywiYWRkcmVzcyI6Iuahpea0niIsIm1vbmV5IjozNTAwLCJuYW1lIjoibGFvd2FuZyJ9.722p5rzc-mbb2Mz7Cdg3revT4GuA4Qp_P6N54S0uUj4";
        Claims claims = Jwts.parser()
                .setSigningKey("youyue")  //指定秘钥
                .parseClaimsJws(token)  //要解析的令牌
                .getBody();//获取解析后的数据
        System.out.println(claims.toString());
    }
}
