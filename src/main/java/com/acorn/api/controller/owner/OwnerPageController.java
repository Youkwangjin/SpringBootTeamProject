package com.acorn.api.controller.owner;

import com.acorn.api.model.owner.Owner;
import com.acorn.api.service.owner.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OwnerPageController {

    private final OwnerService ownerService;

    @GetMapping("/owner/join")
    public String ownerJoinPage() {
        return "owner/owner-join";
    }

    @GetMapping("/owner/login")
    public String ownerLoginPage() {
        return "owner/owner-login";
    }

    @GetMapping("/owner/mypage")
    public String mypage(Model model) {
        Owner ownerData = ownerService.getOwnerData();
        model.addAttribute("ownerName", ownerData.getOwnerName());
        return "common/mypage";
    }

    @GetMapping("/owner/update/profile")
    public String userUpdatePage(Model model) {
        Owner ownerData = ownerService.getOwnerData();
        model.addAttribute("ownerData", ownerData);
        return "owner/owner-update";
    }

    @GetMapping("/owner/delete/profile")
    public String userDeletePage(Model model) {
        Owner ownerData = ownerService.getOwnerData();
        model.addAttribute("ownerData", ownerData);
        return "owner/owner-delete";
    }
}
