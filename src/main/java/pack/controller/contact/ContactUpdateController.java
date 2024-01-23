package pack.controller.contact;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactUpdateService;

@Controller
@AllArgsConstructor
public class ContactUpdateController {

    private final ContactUpdateService contactUpdateService;

    @PostMapping("/contactUpdate")
    public String contactUpdate(ContactDTO contactDTO) {
        boolean updateContactData = contactUpdateService.updateContact(contactDTO);
        if (updateContactData)
            return "redirect:/contactAdmin?page=1";
        else
            return "/container/container-error";
    }
}

