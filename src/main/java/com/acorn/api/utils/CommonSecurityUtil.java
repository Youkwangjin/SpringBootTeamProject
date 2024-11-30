package com.acorn.api.utils;

import com.acorn.api.security.owner.CustomOwnerDetails;
import com.acorn.api.security.user.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonSecurityUtil {

    private static final Logger log = LoggerFactory.getLogger(CommonSecurityUtil.class);

    public static String getAuthenticatedUUId() {
        Object principal = getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserUUId();
        } else if (principal instanceof CustomOwnerDetails) {
            return ((CustomOwnerDetails) principal).getOwnerUUId();
        }

        return null;
    }

    public static String getAuthenticatedName() {
        Object principal = getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserDisplayName();
        } else if (principal instanceof CustomOwnerDetails) {
            return ((CustomOwnerDetails) principal).getOwnerName();
        }

        return null;
    }

    private static Object getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            try {
                return authentication.getPrincipal();
            } catch (Exception e) {
                log.error(" =============== PrincipalData Not Found : {} =============== ", e.getMessage());
            }
        }

        return null;
    }
}