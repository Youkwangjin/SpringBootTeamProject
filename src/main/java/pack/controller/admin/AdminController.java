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
	public String adminlogingo() {
		return "../templates/admin/adminlogin";
	}

    @PostMapping("adminloginok")
    public String processLoginForm(@RequestParam("admin_id") String admin_id,
            					   @RequestParam("admin_pwd") String admin_pwd,
                                   Model model, HttpSession session){
        // 사용자 로그인 처리
    	AdminDTO admin = adminDao.adminLoginProcess(admin_id, admin_pwd);

        if (admin != null) {
        	session.setAttribute("adminSession", admin);
        	return "admin/adminloginok";
            
        } else {
            // 로그인 실패
            return "admin/adminlogin"; 
        }
    }

	@GetMapping("/adminlogout")
	public String userLogoutProcess(HttpSession session) {
	    session.removeAttribute("adminSession");
	    return "redirect:/";
	}
	

	@GetMapping("/adminsessionkeep")
	public String adminSessionKeep(HttpSession session) {
		AdminDTO adminSession = (AdminDTO) session.getAttribute("adminSession");
	    if (adminSession != null) {
	        return "admin/adminloginok"; 
	    } else {
	    	return "/resources/templates/index/index.html";
	    }
	}
}
