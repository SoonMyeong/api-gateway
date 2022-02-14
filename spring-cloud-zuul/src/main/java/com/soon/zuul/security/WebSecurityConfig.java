package com.soon.zuul.security;

import com.soon.zuul.security.filter.LoginProcessingFilter;
import com.soon.zuul.security.handler.LoginAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

    private final LoginAuthenticationSuccessHandler successHandler;

    @Autowired
    public WebSecurityConfig(
        LoginAuthenticationSuccessHandler successHandler
    )
    {
        this.successHandler = successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests()
            .mvcMatchers("/login").permitAll()
            .mvcMatchers("/api/**").permitAll()
            .anyRequest().authenticated()
            .and()
                .addFilterBefore(loginProcessiongFilter(), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disable session
            .and()
            .csrf().disable();
    }

    @Bean
    public LoginProcessingFilter loginProcessiongFilter() throws Exception
    {
        LoginProcessingFilter loginProcessingFilter = new LoginProcessingFilter();
        loginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        loginProcessingFilter.setAuthenticationSuccessHandler(successHandler);

        return loginProcessingFilter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

}
