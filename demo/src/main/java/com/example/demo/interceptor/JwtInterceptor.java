package com.example.demo.interceptor;

import com.example.demo.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(

            HttpServletRequest request,

            HttpServletResponse response,

            Object handler

    ) throws Exception {

        // FIX CORS PREFLIGHT
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {

            response.setStatus(HttpServletResponse.SC_OK);

            return true;
        }

        String authHeader = request.getHeader("Authorization");

        // CHECK TOKEN
        if (authHeader == null  || !authHeader.startsWith("Bearer ")) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                        "message":"Missing token"
                    }
                    """);

            return false;
        }

        try {

            // GET TOKEN
            String token = authHeader.substring(7);
            System.out.println(token);
            // VALIDATE
            boolean valid = JwtUtils.validateToken(token);

            if (!valid) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                response.setContentType("application/json");

                response.getWriter().write("""
                        {
                            "message":"Invalid token"
                        }
                        """);

                return false;
            }

            // GET USERNAME
            String username = JwtUtils.getUsername(token);

            // SAVE REQUEST
            request.setAttribute("username", username);
            return true;

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                        "message":"Unauthorized"
                    }
                    """);

            return false;
        }
    }

}