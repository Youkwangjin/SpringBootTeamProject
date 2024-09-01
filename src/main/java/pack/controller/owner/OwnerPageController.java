package pack.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pack.model.owner.Owner;
import pack.model.user.User;
import pack.service.owner.OwnerService;

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
        model.addAttribute("userName", ownerData.getOwnerName());
        return "common/mypage";
    }
}
