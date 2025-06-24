package com.acorn.api.controller.faq;

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
public class FaqPageController {

    private final FaqService faqService;

    @GetMapping("/faq/list")
    public String faqListPage(CommonListReqDTO listData, Model model) {
        List<FaqListResDTO> faqListData = faqService.getFaqListData(listData);
        model.addAttribute("faqListData", faqListData);
        model.addAttribute("request", listData);
        return "faq/faq-list";
    }
}