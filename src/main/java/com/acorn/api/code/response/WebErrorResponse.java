package com.acorn.api.code.response;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class WebErrorResponse {

    public ModelAndView response(HttpServletRequest request, HttpServletResponse response) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            statusCode = response.getStatus();
        }

        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        log.error("Error occurred Status: {}, Request URI: {}", httpStatus, request.getRequestURI());

        ModelAndView mav = new ModelAndView("common/error");
        mav.addObject("httpStatus", httpStatus);
        mav.addObject("resultCd", httpStatus.value());
        mav.addObject("resultMsg", getErrorMessage(httpStatus));

        return mav;
    }

    private String getErrorMessage(HttpStatus status) {
        return switch (status) {
            case UNAUTHORIZED -> "로그인 후 이용해주세요.";
            case FORBIDDEN -> "접근 권한이 없습니다.";
            case NOT_FOUND -> "요청하신 페이지를 찾을 수 없습니다.";
            case INTERNAL_SERVER_ERROR -> "서버 오류가 발생했습니다.";
            default -> "알 수 없는 오류가 발생했습니다.";
        };
    }
}