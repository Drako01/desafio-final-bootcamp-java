package com.educacionit.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RequestInterceptorConfig implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String currentUrl = request.getRequestURI();
        int lastSlashIndex = currentUrl.lastIndexOf("/");
        String lastVisitedUrl = currentUrl.substring(0, lastSlashIndex + 1);
        session.setAttribute("lastVisitedUrl", lastVisitedUrl);
        return true;
    }
}
