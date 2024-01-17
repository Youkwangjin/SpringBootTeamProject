package pack.service.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.admin.AdminDAO;
import pack.dto.admin.AdminDTO;
import jakarta.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminDAO adminDao;

    public String processLogin(String admin_id, String admin_pwd, HttpSession session) {
        AdminDTO admin = adminLoginProcess(admin_id, admin_pwd);
        if (admin != null) {
            session.setAttribute("adminSession", admin);
            return "admin/admin-loginok";
        } else {
            return "admin/admin-login";
        }
    }

    public AdminDTO adminLoginProcess(String admin_id, String admin_pwd) {
        return adminDao.adminLoginProcess(admin_id, admin_pwd);
    }
}
