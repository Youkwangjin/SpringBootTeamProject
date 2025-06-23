package com.acorn.api.controller.admin;

import com.acorn.api.dto.admin.response.AdminContainerDetailResDTO;
import com.acorn.api.dto.admin.response.AdminResDTO;
import com.acorn.api.dto.container.request.ContainerListReqDTO;
import com.acorn.api.dto.container.response.ContainerListResDTO;
import com.acorn.api.service.admin.AdminContainerService;
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
    private final AdminContainerService adminContainerService;

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "/admin/admin-login";
    }

    @GetMapping("/admin/mypage")
    public String adminPage(Model model) {
        AdminResDTO adminData = adminService.getAdminData();
        model.addAttribute("adminNm", adminData.getAdminNm());
        return "common/mypage";
    }

    @GetMapping("/admin/container/list")
    public String containersListPage(ContainerListReqDTO listData, Model model) {
        List<ContainerListResDTO> containerListData = adminContainerService.getContainerList(listData);
        model.addAttribute("containerList", containerListData);
        model.addAttribute("request", listData);
        return "admin/admin-container-list";
    }

    @GetMapping("/admin/container/detail/{containerId}")
    public String containerDetailPage(@PathVariable("containerId") Integer containerId, Model model) {
        AdminContainerDetailResDTO containerDetailData = adminContainerService.getContainerData(containerId);
        model.addAttribute("containerDetail", containerDetailData);
        return "admin/admin-container-detail";
    }
}