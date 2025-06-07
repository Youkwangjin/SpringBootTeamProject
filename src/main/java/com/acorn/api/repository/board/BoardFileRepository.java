package com.acorn.api.repository.board;

import com.acorn.api.entity.board.BoardFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardFileRepository {
    Integer selectBoardFileIdKey();

    void saveBoardFile(BoardFile boardFile);

    List<BoardFile> selectFilesByBoardId(@Param("boardId") Integer boardId);

    BoardFile selectFilesByBoardFileId(@Param("boardFileId") Integer boardFileId);

    void deleteBoardFile(@Param("boardFileId") Integer boardFileId);
}