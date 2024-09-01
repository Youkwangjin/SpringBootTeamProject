package pack.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pack.model.user.User;


public class UserSecurityUtil {

    private static final Logger log = LoggerFactory.getLogger(UserSecurityUtil.class);

    public static String getAuthenticatedUUId() {
        User userUUIdData = getUserPrincipal();

        if (userUUIdData != null) {
            return userUUIdData.getUserUUId();
        }
        return null;
    }

    public static String getAuthenticatedTelNumber() {
        User userTelData = getUserPrincipal();

        if (userTelData != null) {
            return userTelData.getUserTel();
        }
        return null;
    }

    private static User getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            try {
                return (User) authentication.getPrincipal();
            } catch (Exception e) {
                log.error(" =============== UserData Not Found : {} =============== ", e.getMessage());
            }
        }
        return null;
    }
}
