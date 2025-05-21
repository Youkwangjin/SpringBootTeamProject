package com.acorn.api.config;

import com.acorn.api.security.admin.CustomAdminJsonAuthenticationFilter;
import com.acorn.api.security.admin.CustomAdminLoginFailureHandler;
import com.acorn.api.security.admin.CustomAdminLoginSuccessHandler;
import com.acorn.api.security.common.*;
import com.acorn.api.security.owner.CustomOwnerJsonAuthenticationFilter;
import com.acorn.api.security.owner.CustomOwnerLoginFailureHandler;
import com.acorn.api.security.owner.CustomOwnerLoginSuccessHandler;
import com.acorn.api.security.user.CustomUserJsonAuthenticationFilter;
import com.acorn.api.security.user.CustomUserLoginFailureHandler;
import com.acorn.api.security.user.CustomUserLoginSuccessHandler;
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

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserLoginSuccessHandler customUserLoginSuccessHandler;
    private final CustomUserLoginFailureHandler customUserLoginFailureHandler;
    private final CustomOwnerLoginSuccessHandler customOwnerLoginSuccessHandler;
    private final CustomOwnerLoginFailureHandler customOwnerLoginFailureHandler;
    private final CustomAdminLoginSuccessHandler customAdminLoginSuccessHandler;
    private final CustomAdminLoginFailureHandler customAdminLoginFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final UserDetailsService userService;
    private final UserDetailsService ownerService;
    private final UserDetailsService adminService;

    public SpringSecurityConfig(ObjectMapper objectMapper,
                                BCryptPasswordEncoder passwordEncoder,
                                CustomUserLoginSuccessHandler customUserLoginSuccessHandler,
                                CustomUserLoginFailureHandler customUserLoginFailureHandler,
                                CustomOwnerLoginSuccessHandler customOwnerLoginSuccessHandler,
                                CustomOwnerLoginFailureHandler customOwnerLoginFailureHandler,
                                CustomAdminLoginSuccessHandler customAdminLoginSuccessHandler,
                                CustomAdminLoginFailureHandler customAdminLoginFailureHandler,
                                CustomLogoutSuccessHandler customLogoutSuccessHandler,
                                CustomAccessDeniedHandler customAccessDeniedHandler,
                                CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                                @Qualifier("userDetailsServiceImpl") UserDetailsService userService,
                                @Qualifier("ownerDetailsServiceImpl") UserDetailsService ownerService,
                                @Qualifier("adminDetailsServiceImpl") UserDetailsService adminService) {

        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.customUserLoginSuccessHandler = customUserLoginSuccessHandler;
        this.customUserLoginFailureHandler = customUserLoginFailureHandler;
        this.customOwnerLoginSuccessHandler = customOwnerLoginSuccessHandler;
        this.customOwnerLoginFailureHandler = customOwnerLoginFailureHandler;
        this.customAdminLoginSuccessHandler = customAdminLoginSuccessHandler;
        this.customAdminLoginFailureHandler = customAdminLoginFailureHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.userService = userService;
        this.ownerService = ownerService;
        this.adminService = adminService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new CustomCsrfTokenRepository())
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/session/expired"))
                );

        http
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
                                         "/error",
                                         "/session/expired",
                                         "/service",
                                         "/containers/map",
                                         "/api/geocode/**",
                                         "/api/containers/coordinates",
                                         "/user/join",
                                         "/user/login",
                                         "/owner/join",
                                         "/owner/login",
                                         "/board/list/**",
                                         "/board/detail/**",
                                         "/admin/login").permitAll()

                        // Public Common API
                        .requestMatchers("/api/logout",
                                         "/api/auth/check").permitAll()

                        // Public User API
                        .requestMatchers("/api/auth/user/register",
                                         "/api/auth/user/emailCheck",
                                         "/api/auth/user/userTelCheck",
                                         "/api/auth/user/login").permitAll()

                        // Public Owner API
                        .requestMatchers("/api/auth/owner/register",
                                         "/api/auth/owner/emailCheck",
                                         "/api/auth/owner/businessNumCheck",
                                         "/api/auth/owner/ownerTelCheck",
                                         "/api/auth/owner/companyNameCheck",
                                         "/api/auth/owner/login").permitAll()

                        // Protected Common Pages
                        .requestMatchers("/board/write",
                                         "/board/update/**").hasAnyAuthority("ROLE_USER", "ROLE_OWNER", "ROLE_ADMIN")

                        // Protected User Common Pages
                        .requestMatchers("/user/mypage",
                                         "/user/update/profile",
                                         "/user/delete/profile").hasAuthority("ROLE_USER")

                        // Protected Owner Common Pages
                        .requestMatchers("/owner/mypage",
                                         "/owner/update/profile",
                                         "/owner/delete/profile",
                                         "/container/list/**",
                                         "/container/register",
                                         "/container/detail/**",
                                         "/container/update/**").hasAuthority("ROLE_OWNER")

                        // Protected Admin Common Pages
                        .requestMatchers("/admin/mypage",
                                         "/admin/container/list/**",
                                         "/admin/container/detail/**",
                                         "/admin/user/list/**",
                                         "/admin/user/detail/**",
                                         "/admin/user/update/**",
                                         "/admin/owner/list/**",
                                         "/admin/owner/detail/**",
                                         "/admin/owner/update/**").hasAuthority("ROLE_ADMIN")

                        // Protected User API
                        .requestMatchers("/api/user/update/{userId}",
                                         "/api/user/delete/{userId}").hasAuthority("ROLE_USER")

                        // Protected Owner API
                        .requestMatchers("/api/owner/update/{ownerId}",
                                         "/api/owner/delete/{ownerId}",
                                         "/api/container/register",
                                         "/api/container/update/{containerId}",
                                         "/api/container/delete/{containerId}").hasAuthority("ROLE_OWNER")

                        // Protected Admin API
                        .requestMatchers("/api/auth/admin/login",
                                         "/api/admin/container/reviewRequest/{containerId}",
                                         "/api/admin/container/approvalRequest/{containerId}",
                                         "/api/admin/container/rejectRequest/{containerId}",
                                         "/api/admin/container/cancelApproval/{containerId}",
                                         "/api/admin/container/cancelReject/{containerId}",
                                         "/api/admin/user/update/{userId}",
                                         "/api/admin/user/delete/{userId}",
                                         "/api/admin/owner/update/{ownerId}",
                                         "/api/admin/owner/delete/{ownerId}",
                                         "/api/admin/password/confirm").hasAuthority("ROLE_ADMIN")

                        // Protected Common Api
                        .requestMatchers("/api/editor/image/upload",
                                         "/api/board/save",
                                         "/api/board/update/{boardId}",
                                         "/api/board/delete/{boardId}").hasAnyAuthority("ROLE_USER", "ROLE_OWNER", "ROLE_ADMIN")

                        .anyRequest().permitAll()
                );

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                );

        http
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                );

        http
                .addFilterBefore(userJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ownerJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CustomUserJsonAuthenticationFilter userJsonUsernamePasswordAuthenticationFilter() {
        CustomUserJsonAuthenticationFilter customUserJsonAuthenticationFilter = new CustomUserJsonAuthenticationFilter(objectMapper, customUserLoginSuccessHandler, customUserLoginFailureHandler);
        customUserJsonAuthenticationFilter.setAuthenticationManager(userAuthenticationManager());
        return customUserJsonAuthenticationFilter;
    }

    @Bean
    public CustomOwnerJsonAuthenticationFilter ownerJsonUsernamePasswordAuthenticationFilter() {
        CustomOwnerJsonAuthenticationFilter customOwnerJsonAuthenticationFilter = new CustomOwnerJsonAuthenticationFilter(objectMapper, customOwnerLoginSuccessHandler, customOwnerLoginFailureHandler);
        customOwnerJsonAuthenticationFilter.setAuthenticationManager(ownerAuthenticationManager());
        return customOwnerJsonAuthenticationFilter;
    }

    @Bean
    public CustomAdminJsonAuthenticationFilter adminJsonUsernamePasswordAuthenticationFilter() {
        CustomAdminJsonAuthenticationFilter customAdminJsonAuthenticationFilter = new CustomAdminJsonAuthenticationFilter(objectMapper, customAdminLoginSuccessHandler, customAdminLoginFailureHandler);
        customAdminJsonAuthenticationFilter.setAuthenticationManager(adminAuthenticationManager());
        return customAdminJsonAuthenticationFilter;
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

    @Bean
    public AuthenticationManager adminAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(adminService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }
}