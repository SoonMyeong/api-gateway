package com.soon.zuul.security.util;

import com.soon.zuul.security.config.JwtProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtBuildUtilTest {

    private JwtBuildUtil jwtBuildUtil;
    private Key key;

    @BeforeEach
    public void setUp() {
        JwtProperty jwtProperty = new JwtProperty();
        jwtProperty.setIssuer("test");
        jwtProperty.setKey("SecretKey-jwt-key-github.com-SoonMyeong");

        this.jwtBuildUtil = new JwtBuildUtil(jwtProperty);
        this.key = new SecretKeySpec(jwtProperty.getKey().getBytes() , SignatureAlgorithm.HS256.getJcaName());
    }

    @Test
    void create() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User("admin","123",authorities);
        String result = jwtBuildUtil.createJwt(user);
        System.out.println(result);
    }

    @Test
    void parse() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User("admin","123",authorities);

        String token = jwtBuildUtil.createJwt(user);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token).getBody();

        System.out.println(claims.getSubject());

    }
}