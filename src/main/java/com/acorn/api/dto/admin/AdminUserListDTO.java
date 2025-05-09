package com.acorn.api.dto.admin;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminUserListDTO extends PaginationRequest {

    private Integer rowNum;

    private Integer pageNo;

    private String searchType;

    private String searchName;

    private Integer userId;

    private String userEmail;

    private String userNm;

    private String userTel;

    private String userAddr;

    private LocalDateTime userCreated;

    private LocalDateTime userUpdated;

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