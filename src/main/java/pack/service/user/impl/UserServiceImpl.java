package pack.service.user.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pack.role.UserRole;
import pack.dto.user.UserDTO;
import pack.repository.user.UserRepository;
import pack.service.user.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean isEmailDuplicate(String userEmail) {
        return !userRepository.isEmailDuplicate(userEmail);
    }

    // 회원가입
    @Override
    public void userRegister(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getUserPassword());
        userDTO.setUserPassword(encodedPassword);
        userDTO.setUserRole(UserRole.USER);
        userRepository.userRegister(userDTO);
    }
}
