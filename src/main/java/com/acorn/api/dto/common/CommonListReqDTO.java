package com.acorn.api.dto.common;

import com.acorn.api.common.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonListReqDTO extends PaginationRequest {

    private Integer pageNo;

    private String searchType;

    private String searchName;

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