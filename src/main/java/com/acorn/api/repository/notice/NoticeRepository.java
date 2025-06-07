package com.acorn.api.repository.notice;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.notice.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeRepository {

    Integer selectNoticeIdKey();

    Integer selectListCountByRequest(PaginationRequest paginationRequest);

    List<Notice> selectNoticeListData(PaginationRequest paginationRequest);

    Notice selectNoticeDetailData(@Param("noticeId") Integer noticeId);

    void saveNotice(Notice notice);

    void updateNoticeHits(@Param("noticeId") Integer noticeId);

    void updateNotice(Notice notice);

    void deleteNotice(Notice notice);
}