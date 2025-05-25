package com.acorn.api.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage() {
        return "index/index";
    }

    @GetMapping("/service")
    public String servicePage() {
        return "index/service";
    }

    @GetMapping("/containers/map")
    public String containerMapsPage() {
        return "index/container-map";
    }
}