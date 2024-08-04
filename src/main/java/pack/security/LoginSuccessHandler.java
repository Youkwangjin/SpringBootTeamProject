package pack.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pack.api.code.common.ApiSuccessCode;
import pack.api.response.ApiSuccessResponse;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 세션 ID 재생성
        HttpSession sessionCookie = request.getSession(false);
        if (sessionCookie != null) {
            sessionCookie.invalidate();
        }

        sessionCookie = request.getSession(true);

        ApiSuccessCode apiSuccessCode = ApiSuccessCode.LOGIN_SUCCESS;
        ApiSuccessResponse<String> apiSuccessResponse = ApiSuccessResponse.<String>builder()
                .resultCode(apiSuccessCode.getStatus())
                .resultMsg(apiSuccessCode.getMessage())
                .build();

        response.setStatus(apiSuccessCode.getStatus());
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
