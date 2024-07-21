package pack.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pack.security.UserAuthenticationProvider;
import pack.service.login.CustomUserDetailsService;

@Configuration
@RequiredArgsConstructor
public class UserAuthenticationProviderConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserAuthenticationProvider userAuthenticationProvider() {
        return new UserAuthenticationProvider(customUserDetailsService, passwordEncoder);
    }
}
