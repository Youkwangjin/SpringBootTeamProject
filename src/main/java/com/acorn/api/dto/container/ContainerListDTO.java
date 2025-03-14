package com.acorn.api.dto.container;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ContainerListDTO extends PaginationRequest {

    private Integer rowNum;

    private Integer pageNo;

    private String searchType;

    private String searchName;

    private Integer containerId;

    private String containerName;

    private BigDecimal containerSize;

    private Integer containerStatus;

    private Integer containerApprovalStatus;

    private LocalDateTime containerCreated;

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