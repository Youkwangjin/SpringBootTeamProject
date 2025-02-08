package com.acorn.api.security.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
            log.info("Session Expired → Redirect to Session Expiration Page");
            response.sendRedirect("/session/expired");
            return;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}