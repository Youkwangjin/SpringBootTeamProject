package com.acorn.api.controller.contact;

import com.acorn.api.dto.contact.ContactListDTO;
import com.acorn.api.service.contact.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContactPageController {

    private final ContactService contactService;

    @GetMapping("/contact/list")
    public String contactUserListPage(ContactListDTO listData, Model model) {
        List<ContactListDTO> contactListData = contactService.getUserContactList(listData);
        model.addAttribute("contactListData", contactListData);
        model.addAttribute("request", listData);
        return "contact/contact-list";
    }

    @GetMapping("/contact/write")
    public String contactUserWritePage() {
        return "contact/contact-write";
    }
}