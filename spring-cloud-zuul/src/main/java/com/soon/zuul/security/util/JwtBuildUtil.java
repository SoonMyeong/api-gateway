package com.soon.zuul.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtBuildUtil
{

    @Value("${spring.security.jwt.key}")
    private String tokenKey;

    @Value("${spring.security.jwt.key}")
    private String issuer;

    private Key key;

    @Autowired
    public JwtBuildUtil()
    {
        this.key = new SecretKeySpec(tokenKey.getBytes(),SignatureAlgorithm.HS256.getJcaName());
    }

    public String createJwt(User user)
    {
        return Jwts.builder()
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setIssuer(issuer)
            .signWith(key)
            .compact();
    }

}
