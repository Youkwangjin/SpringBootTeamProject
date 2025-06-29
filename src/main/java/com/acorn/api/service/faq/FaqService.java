package com.acorn.api.service.faq;

import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.faq.response.FaqContentsResDTO;
import com.acorn.api.dto.faq.response.FaqListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FaqService {
    List<FaqListResDTO> getFaqListData(CommonListReqDTO listData);

    FaqContentsResDTO getFaqContents(Integer faqId);
}