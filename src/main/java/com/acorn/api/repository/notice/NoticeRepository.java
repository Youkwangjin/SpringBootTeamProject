package com.acorn.api.repository.notice;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.notice.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeRepository {

    Integer selectListCountByRequest(PaginationRequest paginationRequest);

    List<Notice> selectNoticeListData(PaginationRequest paginationRequest);
}