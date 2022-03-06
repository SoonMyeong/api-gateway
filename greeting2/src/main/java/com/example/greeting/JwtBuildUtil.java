package com.example.greeting;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Component
public class JwtBuildUtil
{
    private final Key key;

    public JwtBuildUtil()
    {
        this.key = new SecretKeySpec("SecretKey-jwt-key-github.com-SoonMyeong".getBytes() , SignatureAlgorithm.HS256.getJcaName());
    }


    public Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build().parseClaimsJws(token);
    }

}
