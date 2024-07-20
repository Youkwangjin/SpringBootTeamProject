package pack.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pack.dto.user.CustomUserDetails;
import pack.dto.user.UserDTO;
import pack.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 데이터베이스에서 가져온 username(Id)를 검증하는 로직
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userData = userRepository.findByUserEmail(username);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
