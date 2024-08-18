package pack.service.user;

import org.springframework.stereotype.Service;
import pack.model.user.User;

@Service
public interface UserService {

    boolean isEmailDuplicate(String userEmail);

    void userRegister(User user);

    boolean isTelPhoneDuplicate(String userTel);

    User getUserData();

    void userDataUpdate(User user);
}