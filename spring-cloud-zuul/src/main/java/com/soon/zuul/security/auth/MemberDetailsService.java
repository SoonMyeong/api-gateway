package com.soon.zuul.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MemberDetailsService implements UserDetailsService
{
    private final AuthReadWrapper authReadWrapper;

    @Autowired
    public MemberDetailsService(AuthReadWrapper authReadWrapper)
    {
        this.authReadWrapper = authReadWrapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
