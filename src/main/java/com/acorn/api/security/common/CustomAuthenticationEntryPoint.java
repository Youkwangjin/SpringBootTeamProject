package com.acorn.api.security.common;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiHttpErrorCode httpErrorCode = ApiHttpErrorCode.AUTHENTICATION_ERROR;

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .errorStatus(httpErrorCode.getErrorStatus())
                .errorDivisionCode(httpErrorCode.getErrorDivisionCode())
                .errorMsg(httpErrorCode.getErrorMsg())
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
