package pack.service.user;

import org.springframework.stereotype.Service;
import pack.dto.user.UserDTO;

@Service
public interface UserService {

    boolean isEmailDuplicate(String userEmail);

    void userRegister(UserDTO userDTO);

    boolean isTelPhoneDuplicate(String userTel);
}