package com.acorn.api.dto.payment;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentListDTO extends PaginationRequest {

    private Integer rowNum;

    private Integer pageNo;

    private String searchType;

    private String searchName;

    private Integer paymentId;

    private Integer paymentUserId;

    private Integer paymentAmount;

    private Integer paymentStatus;

    private String containerName;

    private LocalDateTime paymentApproved;

    private LocalDateTime paymentCanceled;

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