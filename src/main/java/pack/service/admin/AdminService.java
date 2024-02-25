package pack.service.admin;

import org.springframework.stereotype.Service;
import pack.dto.admin.AdminDTO;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@Service
public interface AdminService {
    Map<String, Object> processLogin(String adminId, String adminPwd, HttpSession session);

    String approveProcess(AdminDTO adminDTO);

    String denyProcess(AdminDTO adminDTO);
}
