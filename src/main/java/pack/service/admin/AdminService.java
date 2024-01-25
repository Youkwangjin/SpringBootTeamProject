package pack.service.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.admin.AdminDAO;
import pack.dto.admin.AdminDTO;
import jakarta.servlet.http.HttpSession;
import pack.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.Map;

@Service
public interface AdminService {
    Map<String, Object> processLogin(String adminId, String adminPwd, HttpSession session);
    String approveProcess(AdminDTO adminDTO);
    String denyProcess(AdminDTO adminDTO);
}
