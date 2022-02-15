package com.soon.zuul.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon.zuul.security.util.JwtBuildUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
