package com.acorn.api.utils;

import com.acorn.api.security.owner.CustomOwnerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class OwnerSecurityUtil {

    private static final Logger log = LoggerFactory.getLogger(OwnerSecurityUtil.class);

    private static CustomOwnerDetails getOwnerPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            try {
                return (CustomOwnerDetails) authentication.getPrincipal();
            } catch (Exception e) {
                log.error(" =============== OwnerData Not Found : {} =============== ", e.getMessage());
            }
        }
        return null;
    }
}