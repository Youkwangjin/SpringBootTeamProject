package com.acorn.api.repository.faq;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.faq.Faq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FaqRepository {
    Integer selectFaqIdKey();

    Integer selectListCountByRequest(PaginationRequest paginationRequest);

    List<Faq> selectFaqListData(PaginationRequest paginationRequest);

    Faq selectFaqDetailData(@Param("faqId") Integer faqId);

    void saveFaq(Faq faq);

    void updateFaq(Faq faq);

    void deleteFaq(Faq faq);
}