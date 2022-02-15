package com.soon.zuul.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon.zuul.security.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginProcessingFilter extends UsernamePasswordAuthenticationFilter
{

    private final ObjectMapper objectMapper;

    public LoginProcessingFilter(ObjectMapper objectMapper)
    {
        super();
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException
    {
        Member member = null;
        try
        {
            member = objectMapper.readValue(request.getReader(), Member.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getId(),member.getPw());
        setDetails(request,authenticationToken);

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
