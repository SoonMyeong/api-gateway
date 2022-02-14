package com.soon.zuul.security.util;

import com.soon.zuul.security.config.JwtProperty;
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
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .claim("user",user)
            .setIssuer(jwtProperty.getIssuer())
            .signWith(key)
            .compact();
    }

}
