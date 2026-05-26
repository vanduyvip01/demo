package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor
        implements HandlerInterceptor {

    @Override
    public boolean preHandle(

            HttpServletRequest request,

            HttpServletResponse response,

            Object handler

    ) throws Exception {

        // ALLOW OPTIONS REQUEST
        if (
                request.getMethod().equals("OPTIONS")
        ) {
            return true;
        }

        String uri = request.getRequestURI();

        // ALLOW LOGIN
        if (
                uri.startsWith("/auth/login")
        ) {
            return true;
        }

        HttpSession session = request.getSession(false);

        // CHECK SESSION
        if (
                session == null || session.getAttribute("user") == null
        ) {

            response.setStatus(401);

            response.setContentType("application/json");

            response.getWriter().write(
                    """
                    {
                        "code":401,
                        "message":"Unauthorized"
                    }
                    """
            );

            return false;
        }

        return true;
    }
}