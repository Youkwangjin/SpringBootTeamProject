package pack.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.dto.admin.AdminDTO;
import pack.service.admin.AdminService;

@Controller
@AllArgsConstructor
public class AdminController {


	private final AdminService adminService;

	@GetMapping("adminLoginGo")
	public String adminLoginGo() {
		return "/admin/admin-login";
	}


	@GetMapping("/adminLogout")
	public String adminLogoutProcess(HttpSession session) {
		session.removeAttribute("adminSession");
		return "redirect:/";
	}

	@PostMapping("adminLoginOk")
	public String processLoginForm(@RequestParam("admin_id") String admin_id,
								   @RequestParam("admin_pwd") String admin_pwd,
								   HttpSession session){
		return adminService.processLogin(admin_id, admin_pwd, session);
	}

	@GetMapping("/adminSessionKeep")
	public String adminSessionKeep(HttpSession session) {
		AdminDTO adminSession = (AdminDTO) session.getAttribute("adminSession");
	    if (adminSession != null) {
	        return "admin/admin-loginok";
	    } else {
	    	return "/index/index";
	    }
	}
}