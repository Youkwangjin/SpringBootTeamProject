package com.acorn.api.security.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSessionExpiredHandler implements SessionInformationExpiredStrategy {

    @Value("${page.url.session.expire}")
    private String ExpiredSessionUrl;

    private static final Logger log = LoggerFactory.getLogger(CustomSessionExpiredHandler.class);

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        log.info(" ================= Session expired detected! Redirecting to login page ================= ");

        HttpServletResponse response = event.getResponse();

        String loginPage = ExpiredSessionUrl;
        response.sendRedirect(loginPage);
    }
}