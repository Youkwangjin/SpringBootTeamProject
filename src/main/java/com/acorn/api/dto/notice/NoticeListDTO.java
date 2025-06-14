package com.acorn.api.dto.notice;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeListDTO extends PaginationRequest {

    private Integer rowNum;

    private Integer pageNo;

    private String searchType;

    private String searchName;

    private Integer noticeId;

    private String noticeTitle;

    private String noticeWriter;

    private Integer noticeHits;

    private LocalDateTime noticeCreated;

    @Override
    public Integer getPageNo() {
        return this.pageNo;
    }

    @Override
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public void setTotalCount(int totalCount) {
        super.setTotalCount(totalCount);
    }
}