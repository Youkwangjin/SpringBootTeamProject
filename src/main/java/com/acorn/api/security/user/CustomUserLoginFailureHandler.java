package com.acorn.api.security.user;

import com.acorn.api.code.user.ApiUserErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomUserLoginFailureHandler implements AuthenticationFailureHandler {

    @Value("${response.content-type}")
    private String contentType;

    @Value("${response.character-encoding}")
    private String characterEncoding;

    @Value("${error.message.default}")
    private String defaultErrorMessage;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ApiUserErrorCode errorCode = ApiUserErrorCode.USER_AUTHENTICATION_FAILED;

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .errorDivisionCode(errorCode.getUserErrorDivisionCode())
                .errorMsg(errorCode.getUserErrorMsg())
                .build();

        response.setStatus(errorResponse.getHttpStatus().value());
        response.setContentType(contentType);
        response.setCharacterEncoding(characterEncoding);
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