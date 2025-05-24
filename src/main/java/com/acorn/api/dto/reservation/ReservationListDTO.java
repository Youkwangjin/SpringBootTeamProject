package com.acorn.api.dto.reservation;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReservationListDTO extends PaginationRequest {

    private Integer rowNum;

    private Integer pageNo;

    private String searchType;

    private String searchName;

    private Integer reservationId;

    private Integer reservationUserId;

    private Integer reservationContainerId;

    private String containerName;

    private String companyName;

    private Integer reservationStatus;

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;

    private LocalDateTime reservationCreated;

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