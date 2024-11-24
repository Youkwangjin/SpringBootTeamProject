package com.acorn.api.utils;

import com.acorn.api.security.owner.CustomOwnerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class OwnerSecurityUtil {

    private static final Logger log = LoggerFactory.getLogger(OwnerSecurityUtil.class);

    public static String getAuthenticatedUUId() {
        CustomOwnerDetails ownerUUId = getOwnerPrincipal();

        if (ownerUUId != null) {
            return ownerUUId.getOwnerUUId();
        }

        return null;
    }

    public static String getAuthenticatedCompanyName() {
        CustomOwnerDetails ownerCompanyName = getOwnerPrincipal();

        if (ownerCompanyName != null) {
            return ownerCompanyName.getOwnerCompanyName();
        }

        return null;
    }

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