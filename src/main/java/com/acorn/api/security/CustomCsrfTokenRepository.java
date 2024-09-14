package com.acorn.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DeferredCsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomCsrfTokenRepository.class);
    private final CsrfTokenRepository delegate = new HttpSessionCsrfTokenRepository();

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        CsrfToken token = delegate.generateToken(request);
        logger.debug(" ******************** Generated CSRF Token ******************** : {}", token.getToken());
        return token;
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token != null) {
            delegate.saveToken(token, request, response);
            logger.debug(" ******************** Saved CSRF Token ******************** : {}", token.getToken());
        } else {
            logger.debug("Attempted to save a null CSRF token");
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return delegate.loadToken(request);
    }

    @Override
    public DeferredCsrfToken loadDeferredToken(HttpServletRequest request, HttpServletResponse response) {
        return CsrfTokenRepository.super.loadDeferredToken(request, response);
    }
}
