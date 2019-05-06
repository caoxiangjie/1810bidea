package com.jk.xys.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().equals("/page/toLogin")||request.getRequestURI().equals("/log/login")||request.getRequestURI().equals("/page/noteLogin")||request.getRequestURI().equals("/log/MessgerCode")||request.getRequestURI().equals("/log/messageLogin"))
        {
            return true;
        }
        //验证session是否存在
        Object obj = request.getSession().getAttribute("user");
        if(obj == null)
        {
            response.sendRedirect("/page/toLogin");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
