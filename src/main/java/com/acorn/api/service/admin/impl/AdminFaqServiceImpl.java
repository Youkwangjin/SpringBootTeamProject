package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.admin.request.AdminFaqRegisterReqDTO;
import com.acorn.api.dto.admin.request.AdminFaqUpdateReqDTO;
import com.acorn.api.dto.admin.response.AdminFaqDetailResDTO;
import com.acorn.api.entity.faq.Faq;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.faq.FaqRepository;
import com.acorn.api.service.admin.AdminFaqService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminFaqServiceImpl implements AdminFaqService {

    private final FaqRepository faqRepository;

    @Override
    public AdminFaqDetailResDTO getFaqDetailData(Integer faqId) {
        Faq detailData = faqRepository.selectFaqDetailData(faqId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.FAQ_NOT_FOUND);
        }

        final String faqTitle = detailData.getFaqTitle();
        final String faqContents = detailData.getFaqContents();
        final String faqContentsText = detailData.getFaqContentsText();
        final LocalDateTime faqCreated = detailData.getFaqCreated();
        final LocalDateTime faqUpdated = detailData.getFaqUpdated();

        return AdminFaqDetailResDTO.builder()
                .faqId(faqId)
                .faqTitle(faqTitle)
                .faqContents(faqContents)
                .faqContentsText(faqContentsText)
                .faqCreated(faqCreated)
                .faqUpdated(faqUpdated)
                .build();
    }

    @Override
    @Transactional
    public void faqRegister(AdminFaqRegisterReqDTO registerData) {
        final Integer faqId = faqRepository.selectFaqIdKey();
        final Integer faqAdminId = AdminSecurityUtil.getCurrentAdminId();
        final String faqTitle = registerData.getFaqTitle();
        final String faqContents = registerData.getFaqContents();
        final String faqContentsText = Jsoup.parse(faqContents).text();

        Faq saveFaqData = Faq.builder()
                .faqId(faqId)
                .faqAdminId(faqAdminId)
                .faqTitle(faqTitle)
                .faqContents(faqContents)
                .faqContentsText(faqContentsText)
                .build();

        faqRepository.saveFaq(saveFaqData);
    }

    @Override
    @Transactional
    public void faqUpdate(AdminFaqUpdateReqDTO updateData) {
        final Integer faqId = updateData.getFaqId();
        final Integer faqAdminId = AdminSecurityUtil.getCurrentAdminId();
        final String faqTitle = updateData.getFaqTitle();
        final String faqContents = updateData.getFaqContents();
        final String faqContentsText = Jsoup.parse(faqContents).text();

        Faq detailData = faqRepository.selectFaqDetailData(faqId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.FAQ_NOT_FOUND);
        }

        final Integer currentAdminId = detailData.getFaqAdminId();
        if (!Objects.equals(faqAdminId, currentAdminId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Faq updateFaqData = Faq.builder()
                .faqId(faqId)
                .faqTitle(faqTitle)
                .faqContents(faqContents)
                .faqContentsText(faqContentsText)
                .build();

        faqRepository.updateFaq(updateFaqData);
    }
}