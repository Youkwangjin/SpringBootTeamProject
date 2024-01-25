package pack.service.admin.adminImpl;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.admin.AdminDAO;
import pack.dto.admin.AdminDTO;
import pack.service.admin.AdminService;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;
    @Override
    public Map<String, Object> processLogin(String adminId, String adminPwd, HttpSession session) {
        Map<String, Object> adminResult = new HashMap<>();
        AdminDTO admin = adminDAO.adminLoginProcess(adminId, adminPwd);
        if (admin != null) {
            session.setAttribute("adminSession", admin);
            adminResult.put("status", "성공!");
        } else {
            adminResult.put("status", "실패!");
            adminResult.put("message", "보안 위반 시도가 감지");
        }
        return adminResult;
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
