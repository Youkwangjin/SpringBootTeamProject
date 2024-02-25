package pack.controller.contact;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dto.contact.ContactDTO;
import pack.service.contact.ContactPageService;

@Controller
@RequiredArgsConstructor
public class ContactPageController {

    private final ContactPageService contactPageService;
    // 상수로 선언
    private static final int PLIST = 10;

    @GetMapping("/contact/admin")
    public String listProcess(@RequestParam("page") int page, Model model) {
        int sPage = page <= 0 ? 1 : page;

        ArrayList<ContactDTO> list = (ArrayList<ContactDTO>) contactPageService.listContact();
        ArrayList<ContactDTO> result = contactPageService.getListData(list, sPage, PLIST);

        model.addAttribute("contactAdmin", result);
        model.addAttribute("pageSu", contactPageService.getPageSu(PLIST));
        model.addAttribute("page", sPage);

        return "contact/contact-admin";
    }
}
