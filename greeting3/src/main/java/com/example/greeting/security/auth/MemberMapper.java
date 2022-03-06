package com.example.greeting.security.auth;

import com.example.greeting.security.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("SELECT * FROM member WHERE id = #{id}")
    Member findById(@Param("id") String id);

}
