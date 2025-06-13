package com.acorn.api.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class PaginationRequest {
    private int pageSize = 10;

    private int blockSize = 10;

    private int firstPageNo;

    private int prevPageNo;

    private int startPageNo;

    private int endPageNo;

    private int nextPageNo;

    private int finalPageNo;

    private int totalCount;

    private int startRowNum;

    private int endRowNum;

    public abstract Integer getPageNo();

    public abstract void setPageNo(Integer pageNo);

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.makePaging();
    }

    private void makePaging() {
        int pageNo = (this.getPageNo() != null && this.getPageNo() > 0) ? this.getPageNo() : 1;

        if (this.totalCount == 0) {
            this.setPageNo(1);
            return;
        }

        if (this.pageSize == 0) {
            this.pageSize = 10;
        }

        int finalPage = (this.totalCount + (this.pageSize - 1)) / this.pageSize;

        if (pageNo > finalPage) {
            pageNo = finalPage;
        }

        this.setPageNo(pageNo);

        int startPage = ((pageNo - 1) / this.blockSize) * this.blockSize + 1;
        int endPage = startPage + this.blockSize - 1;

        if (endPage > finalPage) {
            endPage = finalPage;
        }

        this.setFirstPageNo(1);
        this.setPrevPageNo(pageNo > 1 ? pageNo - 1 : 1);
        this.setStartPageNo(startPage);
        this.setEndPageNo(endPage);
        this.setNextPageNo(pageNo < finalPage ? pageNo + 1 : finalPage);
        this.setFinalPageNo(finalPage);
        this.setEndRowNum(pageNo * this.pageSize);
        this.setStartRowNum(this.getEndRowNum() - this.pageSize);
    }
}