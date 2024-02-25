package pack.controller.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactDetailService;

@Controller
@RequiredArgsConstructor
public class ContactDetailController {

    private final ContactDetailService contactDetailService;

    @GetMapping("/contact/detail")
    public String contactDetail(@RequestParam("contact_no") String contact_no, Model model) {
        ContactDTO detail = contactDetailService.detailContact(contact_no);
        model.addAttribute("detail", detail);
        return "contact/contact-detail";
    }
}

