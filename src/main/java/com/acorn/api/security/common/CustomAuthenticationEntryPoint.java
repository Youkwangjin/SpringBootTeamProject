package com.acorn.api.security.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
        로그인하지 않은 사용자가 보호된 리소스에 접근할 때 동작.
     */
    @Value("${page.url.board.write}")
    private String boardWriteUrl;

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info(" ============= Unauthorized access attempt for request ============= : {}", request.getRequestURI());

        String requestURI = request.getRequestURI();

        if (StringUtils.equals(requestURI, boardWriteUrl)) {
            response.sendRedirect("/error/401");
        } else {
            response.sendRedirect("/user/login");
        }
    }
}
