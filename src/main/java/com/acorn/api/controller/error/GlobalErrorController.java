package com.acorn.api.controller.error;

import com.acorn.api.code.response.WebErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class GlobalErrorController {

    private final WebErrorResponse webErrorResponse;

    @GetMapping("/error")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
        return webErrorResponse.response(request, response);
    }
}