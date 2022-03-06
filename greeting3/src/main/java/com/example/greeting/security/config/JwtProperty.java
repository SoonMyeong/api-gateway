package com.example.greeting.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.jwt")
@Getter
@Setter
public class JwtProperty
{
    private String key;
    private String issuer;
}
