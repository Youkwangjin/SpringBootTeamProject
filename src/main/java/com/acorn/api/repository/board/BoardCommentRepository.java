package com.acorn.api.repository.board;

import com.acorn.api.entity.board.BoardComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardCommentRepository {

    Integer selectBoardCommentIdKey();

    void saveBoardComment(BoardComment boardComment);

    List<BoardComment> selectBoardCommentsDetailByBoardId(@Param("boardCommentBoardId") Integer boardId);
}