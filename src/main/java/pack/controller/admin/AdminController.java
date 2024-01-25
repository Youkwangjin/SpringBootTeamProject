package pack.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ResponseBody;
import pack.dto.admin.AdminDTO;
import pack.service.admin.AdminService;

import java.util.Map;


@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/adminLoginGo")
    public String adminLoginGo() {
        return "/admin/admin-login";
    }

    @GetMapping("/adminLogout")
    public String adminLogoutProcess(HttpSession session) {
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

    @PostMapping("/adminLoginOk")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processLoginForm(@RequestParam("admin_id") String adminId,
                                                                @RequestParam("admin_pwd") String adminPwd,
                                                                HttpSession session) {
        Map<String, Object> adminResult = adminService.processLogin(adminId, adminPwd, session);
        return new ResponseEntity<>(adminResult, HttpStatus.OK);

    }

    @PostMapping("/apprProcess")
    public String approveProcess(AdminDTO adminDTO) {
        return adminService.approveProcess(adminDTO);
    }

    @PostMapping("/denyProcess")
    public String denyProcess(AdminDTO adminDTO) {
        return adminService.denyProcess(adminDTO);
    }

}
