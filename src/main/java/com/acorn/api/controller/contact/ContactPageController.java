package com.acorn.api.controller.contact;

import com.acorn.api.dto.contact.ContactDetailDTO;
import com.acorn.api.dto.contact.ContactListDTO;
import com.acorn.api.service.contact.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContactPageController {

    private final ContactService contactService;

    @GetMapping("/contact/list")
    public String contactListPage(ContactListDTO listData, Model model) {
        List<ContactListDTO> contactListData = contactService.getUserContactList(listData);
        model.addAttribute("contactListData", contactListData);
        model.addAttribute("request", listData);
        return "contact/contact-list";
    }

    @GetMapping("/contact/write")
    public String contactWritePage() {
        return "contact/contact-write";
    }

    @GetMapping("/contact/detail/{contactId}")
    public String contactDetailPage(@PathVariable("contactId") Integer contactId, Model model) {
        ContactDetailDTO contactDetailData = contactService.selectContactDetailData(contactId);
        model.addAttribute("contactDetailData", contactDetailData);
        return "contact/contact-detail";
    }

    @GetMapping("/contact/update/{contactId}")
    public String contactUpdatePage(@PathVariable("contactId") Integer contactId, Model model) {
        ContactDetailDTO contactDetailData = contactService.selectContactDetailData(contactId);
        model.addAttribute("contactDetailData", contactDetailData);
        return "contact/contact-update";
    }
}