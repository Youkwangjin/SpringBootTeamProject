package com.acorn.api.controller.admin;

import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.faq.response.FaqListResDTO;
import com.acorn.api.service.faq.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminFaqPageController {

    private final FaqService faqService;

    @GetMapping("/admin/faq/list")
    public String faqListPage(CommonListReqDTO listData, Model model) {
        List<FaqListResDTO> faqListData = faqService.getFaqListData(listData);
        model.addAttribute("faqListData", faqListData);
        model.addAttribute("request", listData);
        return "admin/admin-faq-list";
    }
}