package pack.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("userSession") != null) {
            return "redirect:/user/mypage";
        } else if (session.getAttribute("ownerSession") != null) {
            return "redirect:/owner/mypage";
        } else if (session.getAttribute("adminSession") != null) {
            return "redirect:/admin/mypage";
        }
        return "index/index";
    }
}