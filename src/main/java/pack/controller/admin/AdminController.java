package pack.controller.admin;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/login")
    public String adminLoginGo() {
        return "admin/admin-login";
    }

    @GetMapping("/admin/logout")
    public String adminLogoutProcess(HttpSession session) {
        session.removeAttribute("adminSession");
        return "redirect:/";
    }

    @GetMapping("/admin/mypage")
    public String adminSessionKeep(HttpSession session) {
        AdminDTO adminSession = (AdminDTO) session.getAttribute("adminSession");
        if (adminSession != null) {
            return "admin/admin-mypage";
        } else {
            return "admin/admin-login";
        }
    }

    @PostMapping("/admin/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processLoginForm(@RequestParam("admin_id") String adminId,
                                                                @RequestParam("admin_pwd") String adminPwd,
                                                                HttpSession session) {
        Map<String, Object> adminResult = adminService.processLogin(adminId, adminPwd, session);
        return new ResponseEntity<>(adminResult, HttpStatus.OK);

    }

    @PostMapping("/appr/process")
    public String approveProcess(AdminDTO adminDTO) {
        return adminService.approveProcess(adminDTO);
    }

    @PostMapping("/deny/process")
    public String denyProcess(AdminDTO adminDTO) {
        return adminService.denyProcess(adminDTO);
    }

}
