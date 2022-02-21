package com.soon.zuul.security.util;

import com.soon.zuul.security.config.JwtProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

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
            .setIssuer(jwtProperty.getIssuer())
            .signWith(key)
            .compact();
    }

    public Jws<Claims> extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build().parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        //TODO..
    }

}
