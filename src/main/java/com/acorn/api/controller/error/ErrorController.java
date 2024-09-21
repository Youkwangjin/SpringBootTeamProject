package com.acorn.api.controller.error;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/401")
    public String error401() {
        return "common/401";
    }

    @GetMapping("/error/403")
    public String error403Page() {
        return "common/403";
    }
}
