package com.acorn.api.entity.notice;

import com.acorn.api.entity.admin.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {

    private Integer rowNum;

    private Integer noticeId;

    private String noticeTitle;

    private String noticeContents;

    private String noticeContentText;

    private Integer noticeHits;

    private LocalDateTime noticeCreated;

    private LocalDateTime noticeUpdated;

    private Integer noticeAdminId;

    private Admin admin;

    private List<NoticeFile> noticeFilesList;
}