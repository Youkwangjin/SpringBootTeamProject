package com.acorn.api.controller.user;

import com.acorn.api.dto.user.UserResponseDTO;
import com.acorn.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserPageController {

    private final UserService userService;

    @GetMapping("/user/join")
    public String userJoinPage() {
        return "user/user-join";
    }

    @GetMapping("/user/login")
    public String userLoginPage() {
        return "user/user-login";
    }

    @GetMapping("/user/mypage")
    public String mypage(Model model) {
        UserResponseDTO userData = userService.getUserData();
        model.addAttribute("userName", userData.getUserDisplayName());
        return "common/mypage";
    }

    @GetMapping("/user/update/profile")
    public String userUpdatePage(Model model) {
        UserResponseDTO userData = userService.getUserData();
        model.addAttribute("userData", userData);
        return "user/user-update";
    }

    @GetMapping("/user/delete/profile")
    public String userDeletePage(Model model) {
        UserResponseDTO userData = userService.getUserData();
        model.addAttribute("userData", userData);
        return "user/user-delete";
    }
}
