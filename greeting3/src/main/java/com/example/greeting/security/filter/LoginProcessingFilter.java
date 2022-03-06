package com.example.greeting.security.filter;

import com.example.greeting.security.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter
{

    private final ObjectMapper objectMapper;

    public LoginProcessingFilter(ObjectMapper objectMapper)
    {
        super(new AntPathRequestMatcher("/security/login"));
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException
    {
        Member member = null;
        Member result = new Member();
        try
        {
            member = objectMapper.readValue(request.getReader(), Member.class);

            result.setId(member.getId());
            result.setPw(member.getPw());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(result.getId(), result.getPw());
        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
