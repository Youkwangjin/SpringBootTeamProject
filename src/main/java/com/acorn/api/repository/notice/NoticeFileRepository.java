package com.acorn.api.repository.notice;

import com.acorn.api.entity.notice.NoticeFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeFileRepository {

    Integer selectNoticeFileIdKey();

    void saveNoticeFile(NoticeFile noticeFile);

    List<NoticeFile> selectFilesByNoticeId(@Param("noticeId") Integer noticeId);

    NoticeFile selectFilesByNoticeFileId(@Param("noticeFileId") Integer noticeFileId);

    void deleteNoticeFile(@Param("noticeFileId") Integer noticeFileId);
}