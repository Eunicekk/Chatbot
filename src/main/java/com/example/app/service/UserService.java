package com.example.app.service;

import com.example.app.dto.UserDto;
import com.example.app.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    // 회원 등록
    public void register(UserDto userDto){
        if(userDto == null){
            throw new IllegalArgumentException("회원 정보 누락");
        }
        userMapper.insert(userDto);
    };

    /**
     * 회원 번호 조회
     * @param userId
     * @param userPassword
     * @return
     * @throws IllegalArgumentException 존재하지 않는 회원 id, password로 조회하는 경우
     */
    @Transactional(readOnly = true)
    public Long findUserNumber(String userId, String userPassword) {
        if(userId == null || userPassword == null){
            throw new IllegalArgumentException("아이디 혹은 패스워드 누락");
        }
        return Optional.ofNullable(userMapper.selectUserNumber(userId, userPassword))
                .orElseThrow(()->{throw new IllegalArgumentException("존재하지 않는 회원입니다.");});
    }
}
