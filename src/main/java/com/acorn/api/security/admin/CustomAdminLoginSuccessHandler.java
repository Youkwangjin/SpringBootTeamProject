package com.acorn.api.security.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAdminLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";
    @Value("${error.message.default}")
    private String defaultErrorMessage;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession oldSession = request.getSession();
        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession newSession = request.getSession();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        newSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

        ApiSuccessCode apiSuccessCode = ApiSuccessCode.LOGIN_SUCCESS;

        ApiSuccessResponse<String> apiSuccessResponse = ApiSuccessResponse.<String>builder()
                .httpStatus(apiSuccessCode.getHttpStatus())
                .resultMsg(apiSuccessCode.getMessage())
                .build();

        response.setStatus(apiSuccessCode.getHttpStatus().value());
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(convertObjectToJson(apiSuccessResponse));
    }

    private String convertObjectToJson(ApiSuccessResponse<?> successResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(successResponse);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"" + defaultErrorMessage + "\"}";
        }
    }
}