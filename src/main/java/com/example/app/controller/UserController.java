package com.example.app.controller;

import com.example.app.dto.UserDto;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public void join(){}

    @PostMapping("/join")
    public RedirectView join(UserDto userDto){
        userService.register(userDto);
        return new RedirectView("/user/login");
    }

    @GetMapping("/login")
    public void login(){}

    @PostMapping("/login")
    public RedirectView login(String userId, String userPassword, HttpServletRequest req){
        try{
            Long userNumber = userService.findUserNumber(userId, userPassword);
            req.getSession().setAttribute("userNumber", userNumber);
        }catch (Exception e){
            e.printStackTrace();
            return new RedirectView("/user/login");
        }
        return new RedirectView("/board/list");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        req.getSession().invalidate(); // 세션 초기화
        return "/board/list";
    }
}
