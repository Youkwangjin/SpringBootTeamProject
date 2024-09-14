package com.acorn.api.security;


import com.acorn.api.com.acorn.api.code.code.user.ApiUserErrorCode;
import com.acorn.api.com.acorn.api.code.response.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ApiUserErrorCode errorCode = ApiUserErrorCode.USER_AUTHENTICATION_FAILED;

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .errorStatus(errorCode.getUserErrorStatus())
                .errorDivisionCode(errorCode.getUserErrorDivisionCode())
                .errorMsg(errorCode.getUserErrorMsg())
                .build();

        response.setStatus(errorResponse.getErrorStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(convertObjectToJson(errorResponse));
    }

    private String convertObjectToJson(ApiErrorResponse errorResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"서버에 내부적으로 오류가 발생했습니다. 잠시 후 다시 시도해주세요.\"}";
        }
    }
}

