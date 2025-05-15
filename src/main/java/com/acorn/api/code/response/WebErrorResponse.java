package com.acorn.api.code.response;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
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
        for (ApiHttpErrorCode errorCode : ApiHttpErrorCode.values()) {
            if(errorCode.getHttpStatus() == status) {
                return errorCode.getErrorMsg();
            }
        }

        for (ApiErrorCode errorCode : ApiErrorCode.values()) {
            if (errorCode.getHttpStatus() == status) {
                return errorCode.getErrorMsg();
            }
        }

        return status.getReasonPhrase();
    }
}