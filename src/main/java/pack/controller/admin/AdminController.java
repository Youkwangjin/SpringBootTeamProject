package pack.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.dao.admin.AdminDAO;
import pack.dto.admin.AdminDTO;

@Controller
@AllArgsConstructor
public class AdminController {

	private final AdminDAO adminDao;

	@GetMapping("adminLoginGo") 
	public String adminLoginGo() {
		return "/admin/admin-login";
	}

    @PostMapping("adminLoginOk")
    public String processLoginForm(@RequestParam("admin_id") String admin_id,
            					   @RequestParam("admin_pwd") String admin_pwd,
                                   Model model, HttpSession session){
    	AdminDTO admin = adminDao.adminLoginProcess(admin_id, admin_pwd);

        if (admin != null) {
        	session.setAttribute("adminSession", admin);
        	return "admin/admin-loginok";
            
        } else {
            return "admin/admin-login";
        }
    }

	@GetMapping("/adminLogout")
	public String userLogoutProcess(HttpSession session) {
	    session.removeAttribute("adminSession");
	    return "redirect:/";
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
