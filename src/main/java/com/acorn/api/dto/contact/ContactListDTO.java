package com.acorn.api.dto.contact;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ContactListDTO extends PaginationRequest {

    private Integer rowNum;

    private Integer pageNo;

    private String searchType;

    private String searchName;

    private Integer contactId;

    private Integer contactUserId;

    private Integer contactOwnerId;

    private Integer contactAdminId;

    private String contactTitle;

    private Integer contactStatus;

    private Integer contactWriterType;

    private String contactAnswerYn;

    private LocalDateTime contactCreated;

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