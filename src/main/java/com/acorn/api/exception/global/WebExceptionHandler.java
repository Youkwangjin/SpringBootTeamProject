package com.acorn.api.exception.global;

import com.acorn.api.code.response.WebErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice(annotations = Controller.class)
@RequiredArgsConstructor
public class WebExceptionHandler {

    private final WebErrorResponse webErrorResponse;

    @ExceptionHandler(AcontainerException.class)
    public ModelAndView handleAcontainerException(HttpServletRequest request, HttpServletResponse response, AcontainerException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        response.setStatus(httpStatus.value());
        return webErrorResponse.response(request, response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response) {
        return webErrorResponse.response(request, response);
    }
}