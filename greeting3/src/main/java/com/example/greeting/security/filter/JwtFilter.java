package com.example.greeting.security.filter;

import com.example.greeting.security.auth.MemberDetailsService;
import com.example.greeting.security.util.JwtBuildUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 참고 : https://medium.com/javarevisited/spring-security-jwt-authentication-in-detail-bb98b5055b50
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final MemberDetailsService memberDetailsService;
    private final JwtBuildUtil jwtBuildUtil;

    @Autowired
    public JwtFilter(MemberDetailsService memberDetailsService , JwtBuildUtil jwtBuildUtil) {
        this.memberDetailsService = memberDetailsService;
        this.jwtBuildUtil = jwtBuildUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;

        if(header !=null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            username = jwtBuildUtil.parseClaims(token).getBody().getSubject();
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = memberDetailsService.loadUserByUsername(username);

            if (jwtBuildUtil.validateToken(token)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
