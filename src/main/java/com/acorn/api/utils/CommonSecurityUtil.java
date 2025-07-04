package com.acorn.api.utils;

import com.acorn.api.security.owner.CustomOwnerDetails;
import com.acorn.api.security.user.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class CommonSecurityUtil {

    public static Integer getCurrentId() {
        Object principal = getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserId();
        } else if (principal instanceof CustomOwnerDetails) {
            return ((CustomOwnerDetails) principal).getOwnerId();
        }

        return null;
    }

    public static Integer getCurrentUserId() {
        Object principal = getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserId();
        }

        return null;
    }

    public static Integer getCurrentOwnerId() {
        Object principal = getPrincipal();

        if (principal instanceof CustomOwnerDetails) {
            return ((CustomOwnerDetails) principal).getOwnerId();
        }

        return null;
    }

    public static String getAuthenticatedName() {
        Object principal = getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserNm();
        } else if (principal instanceof CustomOwnerDetails) {
            return ((CustomOwnerDetails) principal).getOwnerNm();
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