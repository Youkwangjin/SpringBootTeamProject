package pack.controller.contact;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactService;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @RequestMapping("/contact/admin")
    public String adminList(Model model) {
        List<ContactDTO> list = contactService.listContact();
        model.addAttribute("list", list);
        return "contact/contact-admin";
    }

    @GetMapping("/contact/user")
    public String mainListUser(Model model) {
        List<ContactDTO> list = contactService.listContact();
        model.addAttribute("list", list);
        return "contact/contact-user";
    }

    @GetMapping("/contact/owner")
    public String mainListOwner(Model model) {
        List<ContactDTO> list = contactService.listContact();
        model.addAttribute("list", list);
        return "contact/contact-owner";
    }
}
