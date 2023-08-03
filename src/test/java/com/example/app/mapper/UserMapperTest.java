package com.example.app.mapper;

import com.example.app.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUserId("aaa");
        userDto.setUserPassword("1234");
        userDto.setUserGender("M");
        userDto.setUserEmail("aaa@gmail.com");
        userDto.setUserAddress("제주도");
    }

    @Test
    @DisplayName("회원가입 및 회원번호 조회")
    void insert() {
        userMapper.insert(userDto);
        Long user = userMapper.selectUserNumber(userDto.getUserId(), userDto.getUserPassword());
        assertThat(userDto.getUserNumber()).isEqualTo(user);
    }

    @Test
    void selectUserNumber() {}
}