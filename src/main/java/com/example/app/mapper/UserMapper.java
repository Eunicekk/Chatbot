package com.example.app.mapper;

import com.example.app.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // 회원가입
    public void insert(UserDto userDto);

    // 로그인
    public Long selectUserNumber(@Param("userId")String userId, @Param("userPassword")String userPassword);
}
