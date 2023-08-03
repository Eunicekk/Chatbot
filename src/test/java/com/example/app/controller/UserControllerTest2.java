package com.example.app.controller;

import com.example.app.dto.UserDto;
import com.example.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;


@Slf4j
// Spring MVC 테스트를 진행하기 위한 어노테이션 테스트를 진행하려는 클래스의 정보를 매개변수로 넘겨준다.
@WebMvcTest(UserController.class)   // 특정 컨트롤러와 관련된 빈만 컨테이너에 등록
@ExtendWith(SpringExtension.class)  // JUnit을 사용할 때 스프링 컨테이너 일부 기능을 사용하기 위한 어노테이션
class UserControllerTest2 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean   // Mock 객체를 bean으로 등록한다.
    private UserService userService;

    @Test
    @DisplayName("join 페이지 이동")
    void join() throws Exception {
        // perform() : 요청을 전송한다. 매개변수로 요청 방식과 url을 받는다.
        // andReturn() : 요청에 대한 응답 결과를 가져온다.
        // getModelAndView() : ModelAndView객체를 가져온다.
        // getViewName() : ModelAndView객체에서 View이름을 가져온다.(html파일의 이름)
        // param() : 키와 값을 한쌍으로 파라미터를 전달함
        // getModelMap() : 모델객체를 가져온다.
        // isOk() : 응답의 상태코드가 200번(정상)인지 확인
        // is3xxRedirection() : 응답의 상태코드가 300번대 리다이렉트인지 확인
        doNothing().when(userService).register(any(UserDto.class));

        mockMvc.perform(post("/user/join")
                .param("userId", "aaa")
                .param("userPassword", "1234")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())   // 검증
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원가입 페이지 이동")
    void testJoin() throws Exception {
        mockMvc.perform(get("/user/join"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("login 페이지 이동")
    void login() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인 페이지 이동")
    void testLogin() throws Exception {
        doReturn(1L).when(userService)
                .findUserNumber(any(String.class), any(String.class));

        mockMvc.perform(post("/user/join")
                .param("userId", "aaa")
                .param("userPassword", "1234")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("존재하지 않는 회원인 경우")
    void testLogin2() throws Exception {
        doThrow(IllegalArgumentException.class).when(userService)
                .findUserNumber(any(String.class), any(String.class));

        mockMvc.perform(post("/user/join")
                .param("userId", "aaa")
                .param("userPassword", "1234")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/login"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/user/logout")
                .sessionAttr("userNumber", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}