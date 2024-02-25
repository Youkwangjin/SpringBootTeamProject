package pack.controller.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactUpdateService;

@Controller
@RequiredArgsConstructor
public class ContactUpdateController {

    private final ContactUpdateService contactUpdateService;

    @PostMapping("/contact/update")
    public String contactUpdate(ContactDTO contactDTO) {
        boolean updateContactData = contactUpdateService.updateContact(contactDTO);
        if (updateContactData)
            return "redirect:/contact/admin?page=1";
        else
            return "container/container-error";
    }
}

