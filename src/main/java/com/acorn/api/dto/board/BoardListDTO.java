package com.acorn.api.dto.board;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListDTO extends PaginationRequest {

    private Integer rowNum;

    private Integer pageNo;

    private String searchType;

    private String searchName;

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardContentsText;

    private Integer boardHits;

    private LocalDateTime boardCreated;

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