package com.acorn.api.security.common;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String sessionExpired = request.getParameter("sessionExpired");

        ApiHttpErrorCode errorCode;

        // 세션 만료 처리
        if (StringUtils.equals(sessionExpired, "true")) {
            errorCode = ApiHttpErrorCode.SESSION_EXPIRE;
            log.info(" ============= Session expired detected for request ============= : {}", request.getRequestURI());

        } else {
            // 일반적인 인증되지 않은 접근 처리
            errorCode = ApiHttpErrorCode.AUTHENTICATION_ERROR;
            log.info(" ============= Unauthorized access attempt for request ============= : {}", request.getRequestURI());
        }

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .errorStatus(errorCode.getErrorStatus())
                .errorDivisionCode(errorCode.getErrorDivisionCode())
                .errorMsg(errorCode.getErrorMsg())
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
            log.error(" ============= Error converting ApiErrorResponse to JSON ============= : {}", e.getMessage());
            return "{\"error\":\"서버에 내부적으로 오류가 발생했습니다. 잠시 후 다시 시도해주세요.\"}";
        }
    }
}
