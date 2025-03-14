package com.acorn.api.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage() {
        return "index/index";
    }

    @GetMapping("/containerMaps")
    public String containerMapsPage() {
        return "index/conajax";
    }
}
