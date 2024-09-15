package com.acorn.api.security.owner;

import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOwnerLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession oldSession = request.getSession(false);

        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession newSession = request.getSession(true);

        // 새로운 SecurityContext 생성 및 인증 정보 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // 새로운 세션에 SecurityContext 저장
        newSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

        ApiHttpSuccessCode apiHttpSuccessCode = ApiHttpSuccessCode.LOGIN_SUCCESS;
        ApiSuccessResponse<String> apiSuccessResponse = ApiSuccessResponse.<String>builder()
                .resultCode(apiHttpSuccessCode.getStatus())
                .resultMsg(apiHttpSuccessCode.getMessage())
                .build();

        response.setStatus(apiHttpSuccessCode.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(convertObjectToJson(apiSuccessResponse));

    }
    private String convertObjectToJson(ApiSuccessResponse<?> successResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(successResponse);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"서버에 내부적으로 오류가 발생했습니다. 잠시 후 다시 시도해주세요.\"}";
        }
    }
}
