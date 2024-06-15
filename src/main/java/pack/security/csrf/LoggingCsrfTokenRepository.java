package pack.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DeferredCsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

public class LoggingCsrfTokenRepository implements CsrfTokenRepository {

    private static final Logger logger = LoggerFactory.getLogger(LoggingCsrfTokenRepository.class);
    private final CsrfTokenRepository delegate = new HttpSessionCsrfTokenRepository();

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        CsrfToken token = delegate.generateToken(request);
        logger.debug(" ******************** Generated CSRF Token ******************** : {}", token.getToken());
        return token;
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        delegate.saveToken(token, request, response);
        logger.debug("Saved CSRF Token: {}", token.getToken());
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
