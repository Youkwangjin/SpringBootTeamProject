package com.acorn.api.controller.admin;

import com.acorn.api.dto.admin.AdminResponseDTO;
import com.acorn.api.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final AdminService adminService;

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "/admin/admin-login";
    }

    @GetMapping("/admin/mypage")
    public String adminPage(Model model) {
        AdminResponseDTO adminData = adminService.getAdminData();
        model.addAttribute("adminNm", adminData.getAdminNm());
        return "/admin/admin-mypage";
    }
}