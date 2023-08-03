package com.example.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomError implements ErrorController {
    @GetMapping("/error")
    public String error(HttpServletRequest req){
        // HTTP 상태 코드를 req에게 받을 수 있다.
        // 상태 코드를 얻기 위한 ket는 우리가 외워서 쓸 수 없으므로 RequestDispatcher가 가진 상수를 활용한다.
        Object attribute = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(attribute != null){
            int statusCode = Integer.valueOf(attribute.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return "error/404"; // 404에러 : 요청 페이지를 찾을 수 없을 때
            }
        }
        return "error/500"; // 500애러 : 서버 오류
    }
}
