package com.acorn.api.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SessionExpiredController {

    @GetMapping("/session/expired")
    public String sessionExpired(HttpServletRequest request) {
        return "common/sessionExpired";
    }
}