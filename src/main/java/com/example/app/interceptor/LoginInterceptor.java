package com.example.app.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userNumber = request.getSession().getAttribute("userNumber");
        if(userNumber == null){
            response.sendRedirect("/user/login");
            return false;
        }

        return true;
    }
}
