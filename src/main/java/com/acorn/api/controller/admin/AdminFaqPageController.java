package com.acorn.api.controller.admin;

import com.acorn.api.dto.admin.response.AdminFaqDetailResDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.faq.response.FaqListResDTO;
import com.acorn.api.service.admin.AdminFaqService;
import com.acorn.api.service.faq.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminFaqPageController {

    private final FaqService faqService;
    private final AdminFaqService adminFaqService;

    @GetMapping("/admin/faq/list")
    public String faqListPage(CommonListReqDTO listData, Model model) {
        List<FaqListResDTO> faqListData = faqService.getFaqListData(listData);
        model.addAttribute("faqListData", faqListData);
        model.addAttribute("request", listData);
        return "admin/admin-faq-list";
    }

    @GetMapping("/admin/faq/detail/{faqId}")
    public String faqDetailPage(@PathVariable("faqId") Integer faqId, Model model) {
        AdminFaqDetailResDTO faqDetailData = adminFaqService.getFaqDetailData(faqId);
        model.addAttribute("faqDetailData", faqDetailData);
        return "admin/admin-faq-detail";
    }
}