package com.example.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserControllerTest {
    // ApplicationContext는 스프링 컨테이너의 구현체(인스턴스)이다.
    // WebApplicationContext는 ApplicationContext를 상속받은 타입으로, 웹 어플리케이션과 관련된 정보를 가진다.
    // 서버를 실행하지 않고 컨트롤러를 확인하려면 가짜 요청을 보내야 한다.
    // 가짜 요청을 보내도 스프링 컨테이너가 반응하여 빈 객체를 생성해야 컨트롤러 테스트가 가능하다.
    // WebApplicationContext는 스프링 웹 어플리케이션 관련 정보를 가진 객체이며
    // 요청과 응답 처리, 서블릿 컨테이너 설정, 빈 객체 관리 등을 할 수 있다.
    // 이를 통해 실제 어플리케이션의 설정과 구성을 그대로 사용할 수 있다.
    @Autowired
    private WebApplicationContext webApplicationContext;

    // MockMvc는 실제 서블릿 컨테이너를 사용하지 않고 req, resp를 사용할 수 있는 환경을 제공해준다.
    // MockMvc 객체를 만들기 위해 필요한 것이 WebApplicationContext 객체이다.
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("join 페이지 이동")
    void join() throws Exception {
        // perform() : 요청을 전송한다. 매개변수로 요청 방식과 url을 받는다.
        // andReturn() : 요청에 대한 응답 결과를 가져온다.
        // getModelAndView() : ModelAndView 객체를 가져온다.
        // getViewName() : ModelAndView 객체에서 View 이름을 가져온다. (html 파일의 이름)
        // param() : 키와 값을 한 쌍으로 파라미터로 전달한다.
        // getModelMap() : 모델 객체를 가져온다.
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/user/join"))
                .andReturn().getModelAndView().getViewName());
    }

    @Test
    void testJoin() {
    }

    @Test
    void login() {
    }

    @Test
    void testLogin() {
    }

    @Test
    void logout() {
    }
}