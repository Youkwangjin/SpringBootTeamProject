package pack.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpSession;
import pack.dto.user.UserDTO;
import pack.service.user.UserService;


@Controller
@AllArgsConstructor
public class UserController {


	private final UserService userService;

	@GetMapping("/firstLogin")
	public String LoginGo(HttpSession session) {
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/userSessionKeep";
	    }
	    return "user/user-login";
	}

	@GetMapping("/userJoinGo")
	public String userJoinGo(HttpSession session) {
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/userSessionKeep";
	    }
		return "user/user-join";
	}

	@GetMapping("userLoginGo")
	public String userLoginGo(HttpSession session) {
		if (session.getAttribute("userSession") != null) {
			return "redirect:/userSessionKeep";
		}
		return "user/user-login";
	}

	@GetMapping("/userUpdate")
	public String userUpdatePage(Model model, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("userSession");
		model.addAttribute("userSession", user);
		return "user/user-update";
	}

	@GetMapping("/userDelete")
	public String userDeletePage(Model model, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("userSession");
		model.addAttribute("userSession", user);
		return "user/user-delete";
	}

	@GetMapping("/userSessionKeep")
	public String userSessionKeep(HttpSession session) {
		UserDTO userSession = (UserDTO) session.getAttribute("userSession");
		if (userSession != null) {
			return "user/user-mypage";
		} else {
			return "/index/index";
		}
	}

	@GetMapping("/userLogoutGo")
	public String userLogoutProcess(HttpSession session) {
		session.removeAttribute("userSession");
		return "redirect:/";
	}

	@GetMapping("/userInfoFind")
	public String userInfoFinding() {
		return "user/user-idfind";
	}


	@PostMapping("/userJoinClick")
	public String userLoginOK(UserDTO userDto) {
		return userService.registerUser(userDto);
	}

	@PostMapping("/userLogSuccess")
	public String processLoginForm(@RequestParam("user_id") String userId,
								   @RequestParam("user_pwd") String userPwd,
								   HttpSession session) {
		return userService.processLogin(userId, userPwd, session);
	}


	@PostMapping("/userInfoUpdate")
	public String userInfoUpdate(UserDTO userDto) {
		return userService.userInfoUpdate(userDto);
	}


	@PostMapping("/userInfoDelete")
	public String userInfoDelete(UserDTO userDto, HttpSession session) {
		return userService.userInfoDelete(userDto, session);
	}

	@ResponseBody
	@PostMapping("/userIdCheck")
	public int idCheck(@RequestParam("user_id") String userId) {
		return userService.userIdCheck(userId);
	}

	@ResponseBody
	@PostMapping("/userIdInfoFind")
	public String userIdFindProcess(@RequestParam("user_name") String userName,
									@RequestParam("user_email") String userEmail,
									@RequestParam("user_jumin") String userJumin) {
		return userService.findUserId(userName, userEmail, userJumin);
	}
}
