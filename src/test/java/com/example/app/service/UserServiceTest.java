package com.example.app.service;

import com.example.app.dto.UserDto;
import com.example.app.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Slf4j
@Transactional
class UserServiceTest {
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

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
    @DisplayName("회원 등록")
    void register() {
        doNothing().when(userMapper).insert(any(UserDto.class));
        userService.register(userDto);
        verify(userMapper, times(1)).insert(userDto);
    }

    @Test
    @DisplayName("회원 번호 조회")
    void findUserNumber() {
        doReturn(1L).when(userMapper).selectUserNumber(any(String.class), any(String.class));
        Long number = userService.findUserNumber("aaa", "1234");

        assertThat(number).isEqualTo(1L);
        assertThatThrownBy(()->userService.findUserNumber(null, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("누락");
    }

    @Test
    @DisplayName("회원 번호 조회 : 예외 처리")
    void findUserNumberException(){
        doReturn(null).when(userMapper).selectUserNumber(any(String.class), any(String.class));

        assertThatThrownBy(()->userService.findUserNumber("a", "a"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("존재하지");
    }
}