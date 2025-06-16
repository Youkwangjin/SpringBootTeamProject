package com.acorn.api.controller.admin;

import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import com.acorn.api.service.admin.AdminContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminContactPageController {

    private final AdminContactService adminContactService;

    @GetMapping("/admin/contact/list")
    public String contactList(CommonListReqDTO listData, Model model) {
        List<ContactListResDTO> contactListData = adminContactService.getContactListData(listData);
        model.addAttribute("contactListData", contactListData);
        model.addAttribute("request", listData);
        return "admin/admin-contact-list";
    }
}