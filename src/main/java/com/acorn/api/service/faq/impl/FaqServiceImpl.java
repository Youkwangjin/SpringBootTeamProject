package com.acorn.api.service.faq.impl;

import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.faq.response.FaqListResDTO;
import com.acorn.api.entity.faq.Faq;
import com.acorn.api.repository.faq.FaqRepository;
import com.acorn.api.service.faq.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;

    @Override
    public List<FaqListResDTO> getFaqListData(CommonListReqDTO listData) {
        listData.setTotalCount(faqRepository.selectListCountByRequest(listData));
        List<Faq> faqListData = faqRepository.selectFaqListData(listData);

        return faqListData.stream()
                .map(faqList -> {
                    final Integer rowNum = faqList.getRowNum();
                    final Integer faqId = faqList.getFaqId();
                    final String faqTitle = faqList.getFaqTitle();
                    final String faqContents = faqList.getFaqContents();
                    final String faqContentsText = faqList.getFaqContentsText();
                    final LocalDateTime faqCreated = faqList.getFaqCreated();

                    return FaqListResDTO.builder()
                            .rowNum(rowNum)
                            .faqId(faqId)
                            .faqTitle(faqTitle)
                            .faqContents(faqContents)
                            .faqContentsText(faqContentsText)
                            .faqCreated(faqCreated)
                            .build();
                })
                .collect(Collectors.toList());
    }
}