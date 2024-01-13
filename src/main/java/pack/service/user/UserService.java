package pack.service.user;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.user.UserDAO;
import pack.dto.user.UserDTO;

@Service
@AllArgsConstructor
public class UserService {


    private final UserDAO userDao;

    public String registerUser(UserDTO userDto) {
        boolean isSuccess = userDao.userInsertData(userDto);
        if (isSuccess) {
            return "user/user-login";
        } else {
            return "user/user-join";
        }
    }

    public String processLogin(String userId, String userPwd, HttpSession session) {
        UserDTO user = userDao.userLoginProcess(userId, userPwd);
        if (user != null) {
            session.setAttribute("userSession", user);
            session.setAttribute("user_id", user.getUser_id());
            return "redirect:/userSessionKeep";
        } else {
            return "user/user-login";
        }
    }

    public String userInfoUpdate(UserDTO userDto) {
        if (userDao.userDataUpdate(userDto)) {
            return "user/user-login";
        } else {
            return "user/user-mypage";
        }
    }

    public String userInfoDelete(UserDTO userDto, HttpSession session) {
        if (userDao.userDataDelete(userDto)) {
            session.removeAttribute("userSession");
            return "user/user-login";
        } else {
            return "user/user-delete";
        }
    }

    public String findUserId(String userName, String userEmail, String userJumin) {
        UserDTO user = userDao.userIdFind(userName, userEmail, userJumin);
        if (user != null) {
            return user.getUser_id();
        } else {
            return "not_found";
        }
    }

}

