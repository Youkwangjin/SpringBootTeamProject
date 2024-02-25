package pack.controller.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.service.contact.ContactDeleteService;

@Controller
@RequiredArgsConstructor
public class ContactDeleteController {

    private final ContactDeleteService contactDeleteService;

    @GetMapping("/contact/delete")
    public String contactDelete(@RequestParam("contact_no") String contact_no) {
        boolean insertContactData = contactDeleteService.deleteContact(contact_no);
        if (insertContactData)
            return "redirect:/contact/admin?page=1";
        else
            return "container/container-error";
    }
}
