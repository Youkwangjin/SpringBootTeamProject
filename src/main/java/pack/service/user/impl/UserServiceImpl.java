package pack.service.user.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pack.role.UserRole;
import pack.dto.user.UserDTO;
import pack.repository.user.UserRepository;
import pack.service.user.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    // 이메일 중복 검증
    @Override
    public boolean isEmailDuplicate(String userEmail) {
        return userRepository.isEmailDuplicate(userEmail);
    }
    
    //  전화번호 중복 검증
    @Override
    public boolean isTelPhoneDuplicate(String userTel) {
        return userRepository.isTelDuplicate(userTel);
    }

    // 회원가입
    @Override
    public void userRegister(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getUserPassword());
        UserDTO newUser = UserDTO.builder()
                .userEmail(userDTO.getUserEmail())
                .userPassword(encodedPassword)
                .userName(userDTO.getUserName())
                .userAddress(userDTO.getUserAddress())
                .userTel(userDTO.getUserTel())
                .userRole(UserRole.USER)
                .build();

        userRepository.userRegister(newUser);
    }

}
