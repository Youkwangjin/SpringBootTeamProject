package com.acorn.api.controller.admin;

import com.acorn.api.dto.admin.AdminOwnerListDTO;
import com.acorn.api.service.admin.AdminOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminOwnerPageController {

    private final AdminOwnerService adminOwnerService;

    @GetMapping("/admin/owner/list")
    public String ownerListPage(AdminOwnerListDTO listData, Model model) {
        List<AdminOwnerListDTO> ownerListData = adminOwnerService.getOwnerList(listData);
        model.addAttribute("ownerListData", ownerListData);
        model.addAttribute("request", listData);
        return "admin/admin-owner-list";
    }
}