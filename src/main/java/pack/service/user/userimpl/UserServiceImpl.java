package pack.service.user.userimpl;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dao.user.UserDAO;
import pack.dto.booking.BookingDTO;
import pack.dto.user.UserDTO;
import pack.service.booking.BookingService;
import pack.service.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDao;
    private final BookingService bookingService;

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
    public Map<String, Object> processLogin(String userId, String userPwd, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        UserDTO user = userDao.userLoginProcess(userId, userPwd);
        if (user != null) {
            session.setAttribute("userSession", user);
            session.setAttribute("user_id", user.getUser_id());
            result.put("status", "성공!");
        } else {
            result.put("status", "실패!");
            result.put("message", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return result;
    }

    @Override
    public String userInfoUpdate(UserDTO userDto) {
        if (userDao.userDataUpdate(userDto)) {
            return "user/user-login";
        } else {
            return "user/user-mypage";
        }
    }

    @Transactional
    public String userInfoDelete(UserDTO userDto, HttpSession session) {
        String userId = userDto.getUser_id();
        List<BookingDTO> activeBookings = bookingService.getActiveBookingsForUser(userId);
        if (!activeBookings.isEmpty()) {
            return "user/user-delete-denied";
        } else {
            if (userDao.userDataDelete(userDto)) {
                session.invalidate();
                return "user/user-login";
            } else {
                return "user/user-delete-denied";
            }
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
