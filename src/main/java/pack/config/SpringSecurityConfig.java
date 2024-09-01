package pack.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pack.security.*;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final UserDetailsService userService;
    private final UserDetailsService ownerService;

    public SpringSecurityConfig(ObjectMapper objectMapper,
                                BCryptPasswordEncoder passwordEncoder,
                                CustomLoginSuccessHandler customLoginSuccessHandler,
                                CustomLoginFailureHandler customLoginFailureHandler,
                                CustomLogoutSuccessHandler customLogoutSuccessHandler,
                                @Qualifier("userDetailsServiceImpl") UserDetailsService userService,
                                @Qualifier("ownerDetailsServiceImpl") UserDetailsService ownerService) {
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.customLoginSuccessHandler = customLoginSuccessHandler;
        this.customLoginFailureHandler = customLoginFailureHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.userService = userService;
        this.ownerService = ownerService;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new CustomCsrfTokenRepository())
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/api/logout", "POST"))
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
                                         "/owner/join",
                                         "/owner/login").permitAll()
                        // Public User API
                        .requestMatchers("/api/logout",
                                         "/api/auth/user/register",
                                         "/api/auth/user/emailCheck",
                                         "/api/auth/user/userTelCheck",
                                         "/api/auth/user/login").permitAll()

                        // Public Owner API
                        .requestMatchers("/api/auth/owner/register",
                                         "/api/auth/owner/emailCheck",
                                         "/api/auth/owner/businessNumCheck",
                                         "/api/auth/owner/ownerTelCheck",
                                         "/api/auth/owner/login").permitAll()

                        // Protected Common Pages
                        .requestMatchers("/user/mypage",
                                         "/user/update/profile").hasAuthority("ROLE_USER")

                        // Protected User API
                        .requestMatchers("/api/user/update/{userUUId}",
                                         "/api/user/delete/{userUUId}").hasAuthority("ROLE_USER")

                        .anyRequest().authenticated())

                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler))

                .addFilterBefore(userJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ownerJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CustomUserJsonAuthenticationFilter userJsonUsernamePasswordAuthenticationFilter() {
        CustomUserJsonAuthenticationFilter customUserJsonAuthenticationFilter = new CustomUserJsonAuthenticationFilter(objectMapper, customLoginSuccessHandler, customLoginFailureHandler);
        customUserJsonAuthenticationFilter.setAuthenticationManager(userAuthenticationManager());
        return customUserJsonAuthenticationFilter;
    }

    @Bean
    public CustomOwnerJsonAuthenticationFilter ownerJsonUsernamePasswordAuthenticationFilter() {
        CustomOwnerJsonAuthenticationFilter customOwnerJsonAuthenticationFilter = new CustomOwnerJsonAuthenticationFilter(objectMapper, customLoginSuccessHandler, customLoginFailureHandler);
        customOwnerJsonAuthenticationFilter.setAuthenticationManager(ownerAuthenticationManager());
        return customOwnerJsonAuthenticationFilter;
    }

    @Bean
    @Primary
    public AuthenticationManager userAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);

        return new ProviderManager(provider);
    }

    @Bean
    public AuthenticationManager ownerAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(ownerService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }
}
