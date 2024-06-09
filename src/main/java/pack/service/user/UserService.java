package pack.service.user;

import org.springframework.stereotype.Service;
import pack.dto.user.UserDTO;

@Service
public interface UserService {

    void userRegister(UserDTO userDTO);

}