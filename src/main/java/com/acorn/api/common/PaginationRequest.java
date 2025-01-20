package com.acorn.api.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
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

    public abstract String getPageNo();

    public abstract void setPageNo(String pageNo);

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.makePaging();
    }

    private void makePaging() {
        int pageNo = Integer.valueOf(StringUtils.defaultIfBlank(this.getPageNo(), "1"));

        if (this.getTotalCount() == 0){
            pageNo = 1;
            return;
        }

        if (pageNo == 0)
            pageNo = 1;

        if (this.pageSize == 0)
            this.pageSize = 10;

        int finalPage = (this.getTotalCount() + (this.pageSize - 1)) / this.pageSize;

        if (pageNo > finalPage)
            pageNo = finalPage;

        if (pageNo < 0||pageNo > finalPage)
            pageNo = 1;

        boolean isNowFirst = pageNo==1 ? true : false;
        boolean isNowFinal = pageNo==finalPage ? true : false;
        int startPage = ((pageNo - 1) / this.blockSize) * this.blockSize + 1;
        int endPage = startPage + this.blockSize - 1;

        if (endPage > finalPage) {
            endPage = finalPage;
        }
        this.setFirstPageNo(1);
        this.setPrevPageNo(isNowFirst?1:((pageNo - 1) < 1 ? 1 : (pageNo - 1)));
        this.setStartPageNo(startPage);
        this.setEndPageNo(endPage);
        this.setNextPageNo(isNowFinal?finalPage:((pageNo + 1) > finalPage ? finalPage : (pageNo + 1)));
        this.setEndRowNum(pageNo * this.pageSize);
        this.setStartRowNum(this.getEndRowNum() - this.pageSize);
        this.setFinalPageNo(finalPage);
        this.setPageNo(String.valueOf(pageNo));
    }
}