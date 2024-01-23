package pack.service.user.userimpl;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.user.UserDAO;
import pack.dto.user.UserDTO;
import pack.service.user.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDao;

    private boolean isEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean joinUserData(UserDTO userDto) {
        boolean b;
        b = isEmpty(userDto.getUser_id()) &&
                isEmpty(userDto.getUser_pwd()) &&
                isEmpty(userDto.getUser_name()) &&
                isEmpty(userDto.getUser_tel()) &&
                isEmpty(userDto.getUser_email()) &&
                isEmpty(userDto.getUser_addr()) &&
                userDto.getUser_id().matches("^[a-zA-Z\\d]{4,}$") &&
                userDto.getUser_tel().matches("^[0-9-]+$") &&
                userDto.getUser_jumin().matches("^\\d{6}-\\d{7}$") &&
                userDto.getUser_pwd().equals(userDto.getUser_repwd()) &&
                userDto.getUser_name().matches("^[가-힣]{2,}$") &&
                userDto.getUser_pwd().matches("^.{4,}$") &&
                userDto.getUser_email().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        return b;
    }

    @Override
    public String registerUser(UserDTO userDto) {
        if (joinUserData(userDto)) {
            boolean isSuccess = userDao.userInsertData(userDto);
            if (isSuccess) {
                return "user/user-login";
            } else {
                return "user/user-join";
            }
        } else {
            return "user/user-join";
        }
    }

    @Override
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

    @Override
    public String userInfoUpdate(UserDTO userDto) {
        if (userDao.userDataUpdate(userDto)) {
            return "user/user-login";
        } else {
            return "user/user-mypage";
        }
    }

    @Override
    public String userInfoDelete(UserDTO userDto, HttpSession session) {
        if (userDao.userDataDelete(userDto)) {
            session.removeAttribute("userSession");
            return "user/user-login";
        } else {
            return "user/user-delete";
        }
    }

    @Override
    public String findUserId(String userName, String userEmail, String userJumin) {
        UserDTO user = userDao.userIdFind(userName, userEmail, userJumin);
        if (user != null) {
            return user.getUser_id();
        } else {
            return "not_found";
        }
    }
    @Override
    public int userIdCheck(String userId) {
        return userDao.userIdCheck(userId);
    }
}
