package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pack.model.user.UserDto;


@Controller
public class ConajaxController {
    @GetMapping("/conajax")
    public String index(HttpSession session) {
       UserDto userSession = (UserDto) session.getAttribute("userSession");
       if (userSession != null) {
           return "conajax";
       }                              
       else {
           return "user-login";
       }
    }
}
