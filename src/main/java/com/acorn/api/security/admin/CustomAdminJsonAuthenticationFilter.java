package com.acorn.api.security.admin;

import com.acorn.api.dto.admin.AdminLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomAdminJsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CONTENT_TYPE = "application/json";
    private static final String LOGIN_REQUEST_URL = "/api/auth/admin/login";
    private static final String HTTP_METHOD_POST = "POST";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(LOGIN_REQUEST_URL, HTTP_METHOD_POST);

    private final ObjectMapper objectMapper;

    public CustomAdminJsonAuthenticationFilter(ObjectMapper objectMapper,
                                               AuthenticationSuccessHandler authenticationSuccessHandler,
                                               AuthenticationFailureHandler authenticationFailureHandler) {

        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);

        this.objectMapper = objectMapper;
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (request.getContentType() == null || !CONTENT_TYPE.equals(request.getContentType())) {
            throw new AuthenticationServiceException("Unsupported content type: " + request.getContentType());
        }

        AdminLoginDTO adminLoginData = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), AdminLoginDTO.class);

        String username = adminLoginData.getAdminEmail();
        String password = adminLoginData.getAdminPassword();

        if (username == null || password == null) {
            throw new AuthenticationServiceException("Missing required fields");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}