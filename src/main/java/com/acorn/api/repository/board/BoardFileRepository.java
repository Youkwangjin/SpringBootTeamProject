package com.acorn.api.repository.board;

import com.acorn.api.entity.board.BoardFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardFileRepository {
    Integer selectBoardFileIdKey();

    void boardFileSave(BoardFile boardFile);
}