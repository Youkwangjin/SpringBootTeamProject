package pack.service.admin.adminImpl;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.admin.AdminDAO;
import pack.dto.admin.AdminDTO;
import pack.service.admin.AdminService;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;
    @Override
    public String processLogin(String admin_id, String admin_pwd, HttpSession session) {
        AdminDTO admin = adminLoginProcess(admin_id, admin_pwd);
        if (admin != null) {
            session.setAttribute("adminSession", admin);
            return "admin/admin-loginok";
        } else {
            return "admin/admin-login";
        }
    }

    @Override
    public AdminDTO adminLoginProcess(String admin_id, String admin_pwd) {
        return adminDAO.adminLoginProcess(admin_id, admin_pwd);
    }

    @Override
    public String approveProcess(AdminDTO adminDTO) {
        boolean approveProcessDate = adminDAO.containerApprove(adminDTO);
        if (approveProcessDate)
            return "redirect:/registered";
        else
            return "owner/error";
    }

    @Override
    public String denyProcess(AdminDTO adminDTO) {
        boolean denyProcessDate = adminDAO.containerDeny(adminDTO);
        if (denyProcessDate)
            return "redirect:/registered";
        else
            return "owner/error";
    }

}
