package pack.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pack.model.owner.Owner;

public class OwnerSecurityUtil {

    private static final Logger log = LoggerFactory.getLogger(OwnerSecurityUtil.class);

    public static String getAuthenticatedUUId() {
        Owner ownerUUId = getOwnerPrincipal();

        if (ownerUUId != null) {
            return ownerUUId.getOwnerUUId();
        }

        return null;
    }

    public static String getAuthenticatedEmail() {
        Owner ownerEmail = getOwnerPrincipal();

        if (ownerEmail != null) {
            return ownerEmail.getOwnerEmail();
        }

        return null;
    }

    public static String getAuthenticatedTelNumber() {
        Owner ownerTel = getOwnerPrincipal();

        if (ownerTel != null) {
            return ownerTel.getOwnerTel();
        }

        return null;
    }

    public static String getAuthenticatedCompanyName() {
        Owner ownerCompanyName = getOwnerPrincipal();

        if (ownerCompanyName != null) {
            return ownerCompanyName.getOwnerCompanyName();
        }

        return null;
    }

    private static Owner getOwnerPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            try {
                return (Owner) authentication.getPrincipal();
            } catch (Exception e) {
                log.error(" =============== OwnerData Not Found : {} =============== ", e.getMessage());
            }
        }
        return null;
    }

}
