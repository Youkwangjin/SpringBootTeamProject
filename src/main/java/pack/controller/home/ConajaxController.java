package pack.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pack.dto.user.UserDTO;


@Controller
public class ConajaxController {
    @GetMapping("/conajax")
    public String index(HttpSession session) {
        UserDTO userSession = (UserDTO) session.getAttribute("userSession");
        if (userSession != null) {
            return "index/conajax";
        } else {
            return "user/user-login";
        }
    }
}
