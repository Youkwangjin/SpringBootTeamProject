package com.acorn.api.repository.board;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.board.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardRepository {
    Integer selectBoardIdKey();

    Integer selectListCountByRequest(PaginationRequest pagination);

    List<Board> selectBoardListData(PaginationRequest pagination);

    void boardSave(Board board);

    Board selectBoardDetailData(@Param("boardId") Integer boardId);

    void updateBoardHits(@Param("boardId")Integer boardId);

    void boardUpdate(Board board);
}