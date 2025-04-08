package com.acorn.api.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "/admin/admin-login";
    }

    @GetMapping("/admin/mypage")
    public String adminPage() {
        return "/admin/admin-mypage";
    }
}