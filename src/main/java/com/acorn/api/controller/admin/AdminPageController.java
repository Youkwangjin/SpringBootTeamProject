package com.acorn.api.controller.admin;

import com.acorn.api.dto.admin.AdminContainerDetailResponseDTO;
import com.acorn.api.dto.admin.AdminResponseDTO;
import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

    @GetMapping("/admin/container/list")
    public String containersListPage(ContainerListDTO listData, Model model) {
        List<ContainerListDTO> containerListData = adminService.getContainerList(listData);
        model.addAttribute("containerList", containerListData);
        model.addAttribute("request", listData);
        return "admin/admin-container-list";
    }

    @GetMapping("/admin/container/detail/{containerId}")
    public String containerDetailPage(@PathVariable("containerId") Integer containerId, Model model) {
        AdminContainerDetailResponseDTO containerDetailData = adminService.getContainerData(containerId);
        model.addAttribute("containerDetail", containerDetailData);
        return "admin/admin-container-detail";
    }
}