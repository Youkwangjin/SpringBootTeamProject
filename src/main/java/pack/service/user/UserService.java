package pack.service.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pack.dto.user.UserDTO;

import java.util.Map;

@Service
public interface UserService {
    String registerUser(UserDTO userDto);
    Map<String, Object> processLogin(String userId, String userPwd, HttpSession session);
    String userInfoUpdate(UserDTO userDto);
    String userInfoDelete(UserDTO userDto, HttpSession session);
    String findUserId(String userName, String userEmail, String userJumin);
    int userIdCheck(String userId);
}

