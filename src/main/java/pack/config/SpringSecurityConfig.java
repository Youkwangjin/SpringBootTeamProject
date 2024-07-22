package pack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import pack.security.UserLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String LOGOUT_PROCESS_URL = "/logoutProcess";

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
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_PROCESS_URL)
                        .logoutSuccessHandler(userLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }


    @Bean
    public SimpleUrlLogoutSuccessHandler userLogoutSuccessHandler() {
        return new UserLogoutSuccessHandler();
    }

}
