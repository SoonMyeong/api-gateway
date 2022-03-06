package com.example.greeting.security.auth;

import com.example.greeting.security.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 실제 DB 에서 값을 조회 하여 UserDetails 타입 리턴 하는 서비스
 */
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
        Member member = authReadWrapper.findById(username);

        if(member == null) {
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getId(),member.getPw(),authorities);
    }

}
