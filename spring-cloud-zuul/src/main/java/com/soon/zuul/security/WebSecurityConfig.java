package com.soon.zuul.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon.zuul.security.auth.MemberDetailsService;
import com.soon.zuul.security.filter.JwtFilter;
import com.soon.zuul.security.filter.LoginProcessingFilter;
import com.soon.zuul.security.handler.LoginAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

    private final LoginAuthenticationSuccessHandler successHandler;
    private final MemberDetailsService memberDetailsService;
    private final ObjectMapper objectMapper;
    private final JwtFilter jwtFilter;

    @Autowired
    public WebSecurityConfig(
        LoginAuthenticationSuccessHandler successHandler
        , MemberDetailsService memberDetailsService
        , ObjectMapper objectMapper
        , JwtFilter jwtFilter
    )
    {
        this.successHandler = successHandler;
        this.memberDetailsService = memberDetailsService;
        this.objectMapper = objectMapper;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(loginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disable session
            .and()
            .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public LoginProcessingFilter loginProcessingFilter() throws Exception
    {
        LoginProcessingFilter loginProcessingFilter = new LoginProcessingFilter(objectMapper);
        loginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        loginProcessingFilter.setAuthenticationSuccessHandler(successHandler);

        return loginProcessingFilter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(memberDetailsService);
        return daoAuthenticationProvider;
    }


    //======================================================================
    // 사용되는 bean 기타 항목들
    //======================================================================

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


}
