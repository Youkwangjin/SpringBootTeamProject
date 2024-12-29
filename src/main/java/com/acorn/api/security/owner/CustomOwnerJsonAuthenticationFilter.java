package com.acorn.api.security.owner;

import com.acorn.api.dto.owner.OwnerLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
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

public class CustomOwnerJsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${response.content-type}")
    private String contentType;
    private static final String LOGIN_REQUEST_URL = "/api/auth/owner/login";
    private static final String HTTP_METHOD_POST = "POST";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(LOGIN_REQUEST_URL, HTTP_METHOD_POST);

    private final ObjectMapper objectMapper;

    public CustomOwnerJsonAuthenticationFilter(ObjectMapper objectMapper,
                                               AuthenticationSuccessHandler authenticationSuccessHandler,
                                               AuthenticationFailureHandler authenticationFailureHandler) {

        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);

        this.objectMapper = objectMapper;
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (request.getContentType() == null || !contentType.equals(request.getContentType())) {
            throw new AuthenticationServiceException("Unsupported content type: " + request.getContentType());
        }

        OwnerLoginDTO ownerLoginData = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), OwnerLoginDTO.class);

        String username = ownerLoginData.getOwnerBusinessNum();
        String password = ownerLoginData.getOwnerPassword();

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