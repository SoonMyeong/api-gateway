package com.example.greeting.security.auth;

import com.example.greeting.security.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthReadWrapper
{
    private final MemberMapper memberMapper;

    @Autowired
    public AuthReadWrapper(MemberMapper memberMapper)
    {
        this.memberMapper = memberMapper;
    }

    public Member findById(String id)
    {
        return memberMapper.findById(id);
    }

}
