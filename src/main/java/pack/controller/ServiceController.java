package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ServiceController {
    @GetMapping("/service")
    public String index() {
        return "service";
    }
}
