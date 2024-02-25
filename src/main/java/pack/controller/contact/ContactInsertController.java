package pack.controller.contact;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactInsertService;

@Controller
@RequiredArgsConstructor
public class ContactInsertController {

    private final ContactInsertService contactInsertService;

    @PostMapping("/insert/contact/user")
    public String insertSubmitUser(ContactDTO contactDTO, Model model) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        model.addAttribute("contact_date", currentDateTime);

        boolean insertContactData = contactInsertService.insertContact(contactDTO);

        if (insertContactData) {
            return "user/user-mypage";
        } else {
            return "container/container-error";
        }
    }

    @PostMapping("/insert/contact/owner")
    public String insertSubmitOwner(ContactDTO contactDTO, Model model) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        model.addAttribute("contact_date", currentDateTime);

        boolean insertContactData = contactInsertService.insertContact(contactDTO);

        if (insertContactData) {
            return "owner/owner-mypage";
        } else {
            return "container/container-error";
        }
    }

}