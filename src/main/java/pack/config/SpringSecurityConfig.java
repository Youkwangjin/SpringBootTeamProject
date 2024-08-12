package pack.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pack.security.CustomJsonAuthenticationFilter;
import pack.security.CustomLoginFailureHandler;
import pack.security.CustomLoginSuccessHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {


    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailureHandler customLoginFailureHandler;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new CustomCsrfTokenRepository())
                )

                //.csrf(AbstractHttpConfigurer::disable) // 테스트용
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        // Static Resource
                        .requestMatchers("/css/**",
                                         "/images/**",
                                         "/assets/**",
                                         "/js/**",
                                         "/assets/img/favicon.png").permitAll()
                        // Public Pages
                        .requestMatchers("/",
                                         "/conajax",
                                         "/user/join",
                                         "/user/login",
                                         "/owner/join").permitAll()
                        // Public API
                        .requestMatchers("/api/auth/user/register",
                                         "/api/auth/user/emailCheck",
                                         "/api/auth/user/userTelCheck",
                                         "/api/auth/user/login").permitAll()

                        // Protected Common Pages
                        .requestMatchers("/mypage").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated())
                .addFilterBefore(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public CustomJsonAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
        CustomJsonAuthenticationFilter customJsonAuthenticationFilter = new CustomJsonAuthenticationFilter(objectMapper, customLoginSuccessHandler, customLoginFailureHandler);
        customJsonAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return customJsonAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);

        return new ProviderManager(provider);
    }
}
