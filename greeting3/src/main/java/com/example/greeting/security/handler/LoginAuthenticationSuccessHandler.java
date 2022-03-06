package com.example.greeting.security.handler;

import com.example.greeting.security.util.JwtBuildUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{

    private final JwtBuildUtil jwtBuildUtil;
    private final ObjectMapper objectMapper;

    @Autowired
    public LoginAuthenticationSuccessHandler(
        JwtBuildUtil jwtBuildUtil,
        ObjectMapper objectMapper
    )
    {
        this.jwtBuildUtil = jwtBuildUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException
    {

        User user = (User) authentication.getPrincipal();
        String token = jwtBuildUtil.createJwt(user);

        objectMapper.writeValue(response.getWriter(),token);
    }
}
