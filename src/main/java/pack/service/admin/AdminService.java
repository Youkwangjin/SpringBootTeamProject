package pack.service.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.admin.AdminDAO;
import pack.dto.admin.AdminDTO;
import jakarta.servlet.http.HttpSession;
import pack.dto.user.UserDTO;

import java.util.ArrayList;

@Service
public interface AdminService {
    String processLogin(String admin_id, String admin_pwd, HttpSession session);
    AdminDTO adminLoginProcess(String admin_id, String admin_pwd);
    String approveProcess(AdminDTO adminDTO);
    String denyProcess(AdminDTO adminDTO);
}
