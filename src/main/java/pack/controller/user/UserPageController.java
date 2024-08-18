package pack.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pack.model.user.User;
import pack.service.user.UserService;

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
        User userData = userService.getUserData();
        model.addAttribute("userUUId", userData.getUserUUId());
        return "common/mypage";
    }

    @GetMapping("/user/update/profile")
    public String userUpdatePage(Model model) {
        User userData = userService.getUserData();
        model.addAttribute("userData", userData);
        return "user/user-update";
    }
}
