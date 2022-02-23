package com.soon.zuul.security.filter;

import com.soon.zuul.security.Member;
import com.soon.zuul.security.util.JwtBuildUtil;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtBuildUtil jwtBuildUtil;

    protected JwtAuthenticationFilter(String defaultFilterProcessesUrl, JwtBuildUtil jwtBuildUtil) {
        super(defaultFilterProcessesUrl);
        this.jwtBuildUtil = jwtBuildUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Not found JWT in request headers");
        }

        String token = header.substring(7);
//        if(jwtBuildUtil.validateToken(token)) {
////            return new UsernamePasswordAuthenticationToken();
//            return "!";
//        }else {
//            return null;
//        }
        return null;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        chain.doFilter(request,response);
    }
}
