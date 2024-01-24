package pack.controller.contact;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactService;

@Controller
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @RequestMapping("/contactAdmin")
    public String adminList(Model model) {
        List<ContactDTO> list = contactService.listContact();
        model.addAttribute("list", list);
        return "contact/contact-admin";
    }

    @RequestMapping("/contactUser")
    public String mainListUser(Model model) {
        List<ContactDTO> list = contactService.listContact();
        model.addAttribute("list", list);
        return "contact/contact-user";
    }

    @RequestMapping("/contactOwner")
    public String mainListOwner(Model model) {
        List<ContactDTO> list = contactService.listContact();
        model.addAttribute("list", list);
        return "contact/contact-owner";
    }
}
