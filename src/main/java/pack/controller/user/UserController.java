package pack.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpSession;
import pack.dto.user.UserDTO;
import pack.service.user.UserService;

import java.util.Map;


@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/join")
    public String userJoinGo(HttpSession session) {
        if (session.getAttribute("userSession") != null) {
            return "redirect:/user/mypage";
        }
        return "user/user-join";
    }

    @GetMapping("/user/login")
    public String userLoginGo(HttpSession session) {
        if (session.getAttribute("userSession") != null) {
            return "redirect:/user/mypage";
        }
        return "user/user-login";
    }

    @GetMapping("/user/update")
    public String userUpdatePage(Model model, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("userSession");
        model.addAttribute("userSession", user);
        if (user != null) {
            return "user/user-update";
        } else {
            return "user/user-login";
        }
    }

    @GetMapping("/user/delete")
    public String userDeletePage(Model model, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("userSession");
        model.addAttribute("userSession", user);
        if (user != null) {
            return "user/user-delete";
        } else {
            return "user/user-login";
        }
    }

    @GetMapping("/user/mypage")
    public String userSession(HttpSession session) {
        UserDTO userSession = (UserDTO) session.getAttribute("userSession");
        if (userSession != null) {
            return "user/user-mypage";
        } else {
            return "user/user-login";
        }
    }

    @GetMapping("/user/logout")
    public String userLogoutProcess(HttpSession session) {
        session.removeAttribute("userSession");
        return "redirect:/";
    }

    @GetMapping("/user/info/find")
    public String userInfoFinding() {
        return "user/user-idfind";
    }


    @PostMapping("/user/join")
    public String userLoginOK(UserDTO userDto) {
        return userService.registerUser(userDto);
    }

    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processLoginForm(@RequestParam("user_id") String userId,
                                                                @RequestParam("user_pwd") String userPwd,
                                                                HttpSession session) {
        Map<String, Object> result = userService.processLogin(userId, userPwd, session);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/user/update")
    public String userInfoUpdate(UserDTO userDto) {
        return userService.userInfoUpdate(userDto);
    }


    @PostMapping("/user/delete")
    public String userInfoDelete(UserDTO userDto, HttpSession session) {
        return userService.userInfoDelete(userDto, session);
    }

    @ResponseBody
    @PostMapping("/user/id/check")
    public int idCheck(@RequestParam("user_id") String userId) {
        return userService.userIdCheck(userId);
    }

    @ResponseBody
    @PostMapping("/user/id/find")
    public String userIdFindProcess(@RequestParam("user_name") String userName,
                                    @RequestParam("user_email") String userEmail,
                                    @RequestParam("user_jumin") String userJumin) {
        return userService.findUserId(userName, userEmail, userJumin);
    }
}
