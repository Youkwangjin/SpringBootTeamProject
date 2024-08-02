package pack.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/user/join")
    public String userJoinPage() {
        return "user/user-join";
    }

    @GetMapping("/user/login")
    public String userLoginPage() {
        return "user/user-login";
    }

    @GetMapping("/user/mypage")
    public String userMyPage() {
        return "user/user-mypage";
    }
}
