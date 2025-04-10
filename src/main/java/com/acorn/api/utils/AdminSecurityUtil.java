package com.acorn.api.utils;

import com.acorn.api.security.admin.CustomAdminDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AdminSecurityUtil {

    public static Integer getCurrentAdminId() {
        Object principal = getPrincipal();

        if (principal instanceof CustomAdminDetails) {
            return ((CustomAdminDetails) principal).getAdminId();
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