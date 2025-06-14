package com.acorn.api.controller.admin;

import com.acorn.api.dto.admin.response.AdminUserListResDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.user.response.UserResDTO;
import com.acorn.api.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUserPageController {

    private final AdminUserService adminUserService;

    @GetMapping("/admin/user/list")
    public String userListPage(CommonListReqDTO listData, Model model) {
        List<AdminUserListResDTO> userListData = adminUserService.getUserList(listData);
        model.addAttribute("userListData", userListData);
        model.addAttribute("request", listData);
        return "admin/admin-user-list";
    }

    @GetMapping("/admin/user/detail/{userId}")
    public String userDetailPage(@PathVariable("userId") Integer userId, Model model) {
        UserResDTO userDetailData = adminUserService.getUserData(userId);
        model.addAttribute("userDetailData", userDetailData);
        return "admin/admin-user-detail";
    }

    @GetMapping("/admin/user/update/{userId}")
    public String userUpdatePage(@PathVariable("userId") Integer userId, Model model) {
        UserResDTO userDetailData = adminUserService.getUserData(userId);
        model.addAttribute("userDetailData", userDetailData);
        return "admin/admin-user-update";
    }
}