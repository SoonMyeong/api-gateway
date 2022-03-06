package com.example.greeting.security.util;

import com.example.greeting.security.config.JwtProperty;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtBuildUtil
{
    private final JwtProperty jwtProperty;
    private final Key key;

    @Autowired
    public JwtBuildUtil(JwtProperty jwtProperty)
    {
        this.jwtProperty = jwtProperty;
        this.key = new SecretKeySpec(jwtProperty.getKey().getBytes() , SignatureAlgorithm.HS256.getJcaName());
    }

    public String createJwt(User user)
    {
        return Jwts.builder()
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 60*30*1000L))
            .claim("user",user)
            .setSubject(user.getUsername())
            .setIssuer(jwtProperty.getIssuer())
            .signWith(key)
            .compact();
    }

    public Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build().parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
