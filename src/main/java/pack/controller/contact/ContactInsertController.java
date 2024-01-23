package pack.controller.contact;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactInsertService;

@Controller
@AllArgsConstructor
public class ContactInsertController {


    private final ContactInsertService contactInsertService;

    @GetMapping("/insertContactUser")
    public String insertUser() {
        return "/contact/contact-user";
    }

    @GetMapping("/insertContactOwner")
    public String insertOwner() {
        return "/contact/contact-owner";
    }

    @PostMapping("/insertContactUser")
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

    @PostMapping("/insertContactOwner")
    public String insertSubmitOwner(ContactDTO contactDTO, Model model) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        model.addAttribute("contact_date", currentDateTime);

        boolean insertContactData = contactInsertService.insertContact(contactDTO);

        if (insertContactData) {
            return "/owner/owner-mypage";
        } else {
            return "container/container-error";
        }
    }

}