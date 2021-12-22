package cn.bdqn.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @title:JwtUtils
 * @Author SwayJike
 * @Date:2021/11/27 23:42
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt.config")
public class JwtUtils {
    private long expire;
    private String secret;
    private String header;

    /**
     * 生成 JWT
     * @param username 用户名
     * @return 返回 JWT
     */
    public String generateJwt(String username){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)  /*设置过期时间*/
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析JWT
     * @param jwt
     * @return
     */
    public Claims getJwtClaim(String jwt){
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * jwt是否过期
     * @param claims
     * @return true过期 ,否则没过期
     */
    public boolean isExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
