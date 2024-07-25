package pack.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pack.security.UserAuthenticationProvider;
import pack.service.user.UserService;

@Configuration
@RequiredArgsConstructor
public class UserAuthenticationProviderConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserAuthenticationProvider userAuthenticationProvider() {
        return new UserAuthenticationProvider(userService, passwordEncoder);
    }
}
