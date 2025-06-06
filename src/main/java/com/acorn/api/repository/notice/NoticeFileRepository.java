package com.acorn.api.repository.notice;

import com.acorn.api.entity.notice.NoticeFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeFileRepository {

    Integer selectNoticeFileIdKey();

    void saveNoticeFile(NoticeFile noticeFile);
}