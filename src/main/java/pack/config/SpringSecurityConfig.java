package pack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import pack.repository.token.LoggingCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new LoggingCsrfTokenRepository())
                )
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        // Static Resource
                        .requestMatchers("/css/**",
                                         "/images/**",
                                         "/assets/**",
                                         "/js/**",
                                         "/assets/img/favicon.png").permitAll()
                        .requestMatchers("/",
                                         "/conajax",
                                         "/user/join",
                                         "/user/register",
                                         "/user/login",
                                         "/owner/join",
                                         "/owner/login").permitAll()
                        .anyRequest().denyAll()
                );
        return http.build();
    }
}
