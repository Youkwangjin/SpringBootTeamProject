package pack.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new LoggingCsrfTokenRepository())
                )
                //.csrf(AbstractHttpConfigurer::disable) // 테스트용
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
                                         "/auth/user/register",
                                         "/auth/user/emailCheck",
                                         "/auth/user/userTelCheck",
                                         "/owner/join").permitAll()
                        .anyRequest().denyAll()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("auth/user/login")
                        .defaultSuccessUrl("/user/mypage", true)
                        .failureUrl("/user/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/user/login")
                        .permitAll()
                );
        return http.build();
    }
}
