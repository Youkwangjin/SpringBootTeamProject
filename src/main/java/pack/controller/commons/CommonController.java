package pack.controller.commons;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @GetMapping("/mypage")
    public String mypage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(" ================================> Authentication at /mypage: {}", auth);

        return "common/mypage";
    }
}
