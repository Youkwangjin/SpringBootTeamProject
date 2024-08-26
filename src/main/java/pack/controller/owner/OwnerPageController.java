package pack.controller.owner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerPageController {

    @GetMapping("/owner/join")
    public String ownerJoinPage() {
        return "owner/owner-join";
    }

    @GetMapping("/owner/login")
    public String ownerLoginPage() {
        return "owner/owner-login";
    }
}
