package com.acorn.api.controller.faq;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.faq.response.FaqContentsResDTO;
import com.acorn.api.service.faq.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FaqContentsController {

    private final FaqService faqService;

    @GetMapping("/api/faq/contents/{faqId}")
    public ResponseEntity<ApiSuccessResponse<Object>> faqContent(@PathVariable("faqId") Integer faqId) {
        FaqContentsResDTO resData = faqService.getFaqContents(faqId);

        return ApiResponseBuilder.success(ApiSuccessCode.FAQ_READ_OK, resData);
    }
}