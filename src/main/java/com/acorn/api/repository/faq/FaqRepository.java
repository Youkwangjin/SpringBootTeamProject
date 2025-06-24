package com.acorn.api.repository.faq;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.faq.Faq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaqRepository {
    Integer selectListCountByRequest(PaginationRequest paginationRequest);

    List<Faq> selectFaqListData(PaginationRequest paginationRequest);
}