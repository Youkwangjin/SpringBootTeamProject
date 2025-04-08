package com.acorn.api.security.admin;

import com.acorn.api.code.admin.ApiAdminErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAdminLoginFailureHandler implements AuthenticationFailureHandler {

    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";
    @Value("${error.message.default}")
    private String defaultErrorMessage;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ApiAdminErrorCode errorCode = ApiAdminErrorCode.ADMIN_AUTHENTICATION_FAILED;

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .errorDivisionCode(errorCode.getAdminErrorDivisionCode())
                .errorMsg(errorCode.getAdminErrorMsg())
                .build();

        response.setStatus(errorResponse.getHttpStatus().value());
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(convertObjectToJson(errorResponse));
    }

    private String convertObjectToJson(ApiErrorResponse errorResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"" + defaultErrorMessage + "\"}";
        }
    }
}